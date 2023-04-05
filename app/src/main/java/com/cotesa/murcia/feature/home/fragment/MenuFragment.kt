package com.cotesa.murcia.feature.home.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cotesa.appcore.extension.*
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.adapter.TabPageAdapter
import com.cotesa.murcia.databinding.FragmentMenuBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.navigator.Navigator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import javax.inject.Inject

class MenuFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator



    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as BeachApplication).appComponent
    }
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        (activity as BaseActivity).clearBackstack()
        homeViewModel = viewModel(viewModelFactory) {
//            observe(listSections, ::renderSections)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView() {
        // Bottom tab bar config
        binding.vpPajer.apply {
            adapter = TabPageAdapter(requireActivity(), 3)
            setCurrentItem(1,false)
            isUserInputEnabled = false
        }


        binding.tlTabLayout.apply {
            selectTab(getTabAt(1),true)
            addOnTabSelectedListener(object : OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab.notNull {
                        binding.vpPajer.currentItem = it.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?){}

                override fun onTabReselected(tab: TabLayout.Tab?){}

            })
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