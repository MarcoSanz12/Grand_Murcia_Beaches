package com.cotesa.murcia.feature.home.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cotesa.appcore.extension.*
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.common.entity.beach.Beach
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.databinding.FragmentListBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.navigator.Navigator
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
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
      /* (activity as BaseActivity).clearBackstack()*/
        homeViewModel = viewModel(viewModelFactory) {
            observe(loaded,::handleLoaded)
            observe(beachList,::handleBeachList)
        }

    }

    private fun handleBeachList(beachList: List<Beach>?){
        binding.tvListPrueba.text = beachList!!.size.toString()
    }

    private fun handleLoaded(b: Boolean?) {
        when(b){
            true,null -> binding.pbSplashLoading.invisible()
            false -> binding.pbSplashLoading.visible()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("ListFragment","Estoy iniciandome " + this::class.java.name)
        initializeView()
    }


    private fun initializeView() {

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