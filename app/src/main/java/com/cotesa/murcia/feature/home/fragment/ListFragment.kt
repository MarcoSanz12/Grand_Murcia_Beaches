package com.cotesa.murcia.feature.home.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cotesa.appcore.extension.*
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.util.*
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.R
import com.cotesa.murcia.adapter.CustomBeachListAdapter
import com.cotesa.murcia.adapter.RecyclerViewOnItemClickListener
import com.cotesa.murcia.databinding.FragmentListBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.home.activity.HomeActivity
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.navigator.Navigator
import okhttp3.internal.notify
import javax.inject.Inject

class ListFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

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
        // Order list by Alphabetical
        val sortedList = beachList?.sortedBy { it.title.toString() }

        binding.rvListRecycle.apply {
            val decoration =
                DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(decoration)
            adapter = CustomBeachListAdapter(sortedList!!, object : RecyclerViewOnItemClickListener<Beach> {
                    override fun onItemClick(v: View?, model: Beach) {

                    }

                    override fun onItemLongClick(v: View?, model: Beach, position:Int) {

                        val prefs = requireContext().getSharedPreferences(Constant.USER_SETTINGS, 0)

                        val favBeachesSet = prefs.getStringSet(Constant.USER_SETTINGS_FAV_BEACHES, null)?: mutableSetOf()

                        // Checks SharedPreferences and adds/removes id from favorites
                        favBeachesSet.apply {
                            val id = model.nid.toString()
                            if (this.contains(id)) {
                                this.remove(id)
                                Toast.makeText(requireContext(), "${model.title.toString()} removed from favorites",  Toast.LENGTH_SHORT).show()
                            } else {
                                this.add(id)
                                Toast.makeText(requireContext(),"${model.title.toString()} added to favorites",Toast.LENGTH_SHORT).show()
                            }
                        }
                        prefs.edit().putStringSet(Constant.USER_SETTINGS_FAV_BEACHES,favBeachesSet).apply()

                        binding.rvListRecycle.adapter!!.notifyItemChanged(position)
                    }
                })
        }
        binding.pbListLoading.invisible()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("ListFragment", "Estoy iniciandome " + this::class.java.name)
        initializeView()
    }


    private fun initializeView() {
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

    private fun searchFunction() {
        TODO("Not yet implemented")
    }

    private fun orderFunction(orderState: OrderState) {
        if (orderState == OrderState.ALPHABETICAL) {

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