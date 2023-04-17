package com.cotesa.murcia.feature.home.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cotesa.appcore.extension.*
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.common.R
import com.cotesa.common.util.DefaultActionBar
import com.cotesa.common.util.HomeActionBar
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.databinding.FragmentMapBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.home.activity.HomeActivity
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.navigator.Navigator
import javax.inject.Inject

class MapFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    override var level: Int = 1

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as BeachApplication).appComponent
    }
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        /* (activity as BaseActivity).clearBackstack()*/
        homeViewModel = viewModel(viewModelFactory) {

        }

    }



    private fun handleLoaded(b: Boolean?) {
        when(b){
            true,null -> binding.pbMapLoading.invisible()
            false -> binding.pbMapLoading.visible()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("MapFragment","Estoy iniciandome " + this::class.java.name)
        initializeView()
    }


    override fun initializeView() {
        with (activity as HomeActivity){
            configureActionBar(DefaultActionBar(getString(R.string.btitle_map)))
        }
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(),"xd", Toast.LENGTH_LONG).show()
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