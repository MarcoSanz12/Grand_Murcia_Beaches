package com.cotesa.murcia.feature.home.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cotesa.appcore.extension.*
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.util.*
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.adapter.CustomBeachListAdapter
import com.cotesa.murcia.adapter.RecyclerViewOnItemClickListener
import com.cotesa.murcia.databinding.FragmentListBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.home.activity.HomeActivity
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.navigator.Navigator
import javax.inject.Inject

class ListFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    lateinit var recyclerAdapter : CustomBeachListAdapter

    override var level: Int = 1

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as BeachApplication).appComponent
    }
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        /* (activity as BaseActivity).clearBackstack()*/
        homeViewModel = viewModel(viewModelFactory) {
            observe(beachList, ::handleLoaded)
        }
    }




    private fun handleLoaded(beachList: List<Beach>?) {
        binding.pbListLoading.visible()


        binding.rvListRecycle.apply {
            adapter = CustomBeachListAdapter(beachList!!, BeachRecyclerViewOnClickItemListener())
            recyclerAdapter = adapter as CustomBeachListAdapter
            sortListWith(BeachComparator(context))
        }
        binding.pbListLoading.invisible()
    }

    private fun sortListWith(comparator: Comparator<Beach>){
        recyclerAdapter.sortItems(comparator)
    }

    inner class BeachRecyclerViewOnClickItemListener : RecyclerViewOnItemClickListener<Beach> {
        override fun onItemClick(v: View?, model: Beach) {
            homeViewModel.selectBeach(model)
            navigator.initDetail(requireActivity() as BaseActivity)
            refreshRecycler()
        }

        override fun onItemLongClick(v: View?, model: Beach, position:Int) {
            updateFavorites(model, position)
        }
    }
    /**
     * When an item in the RecyclerView gets pressed for a long time, checks in [SharedPreferences]
     * for its nid.
     * If it exists, gets removed from there
     * If it doesn't, gets added
     *
     * After all this, gets saved and the item in question gets updated.
     *
     * @param model Clicked Beach item
     * @param position of the [model] in the RecyclerView
     */
    private fun updateFavorites(model: Beach, position: Int) {
        saveFavorites(model,requireContext())
        // Update RecyclerView item
        binding.rvListRecycle.adapter!!.notifyItemChanged(position)
    }

    fun refreshRecycler(){
        recyclerAdapter.sortItems(BeachComparator(requireContext()))
    }
    companion object{
        fun saveFavorites(model: Beach,context: Context) {
            val prefs = context.getSharedPreferences(Constant.USER_SETTINGS, 0)

            val preferencesSet =
                prefs.getStringSet(Constant.USER_SETTINGS_FAV_BEACHES, null) ?: mutableSetOf()
            val favBeachesSet = mutableSetOf<String>().apply { addAll(preferencesSet) }

            // Checks SharedPreferences and adds/removes id from favorites
            favBeachesSet.apply {
                val id = model.nid.toString()
                if (this.contains(id)) {
                    this.remove(id)
                    Toast.makeText(
                        context,
                        "${model.title.toString()} removed from favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    this.add(id)
                    Toast.makeText(
                        context,
                        "${model.title.toString()} added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            // Save changes
            prefs.edit().putStringSet(Constant.USER_SETTINGS_FAV_BEACHES, favBeachesSet).apply()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }


    override fun initializeView() {
        with(activity as HomeActivity) {
            configureActionBar(
                ListActionBar(
                    getString(com.cotesa.common.R.string.btitle_list),
                    ::orderFunction,
                    ::searchFunction
                )
            )
        }
    }

    private fun searchFunction(text:String) {
        recyclerAdapter.filterItems(text)
    }

    private fun orderFunction(orderState: OrderState) {
        if (orderState == OrderState.ALPHABETICAL) {
            sortListWith(BeachComparator(requireContext()))
        }
    }

    fun goToUrl(url: String) {
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        try {
            requireContext().startActivity(launchBrowser)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

}