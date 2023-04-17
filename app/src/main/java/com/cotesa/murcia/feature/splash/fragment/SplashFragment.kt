package com.cotesa.murcia.feature.splash.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cotesa.appcore.extension.observe
import com.cotesa.appcore.extension.viewModel
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.databinding.FragmentSplashBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.splash.viewmodel.SplashViewModel
import com.cotesa.murcia.navigator.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class SplashFragment : BaseFragment() {

    private var login = true
    private var services = false

    override var level: Int = 0

    @Inject
    lateinit var navigator: Navigator
    private lateinit var splashViewModel: SplashViewModel

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as BeachApplication).appComponent
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        splashViewModel = viewModel(viewModelFactory) {
            observe(loaded, ::renderLoaded)
        }
    }


    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    override fun initializeView() {

        CoroutineScope(Dispatchers.Default).launch{
            splashViewModel.callAllBeaches()
        }
            initHandler(2000)

    }

    private fun renderLoaded(loaded: Boolean?) {
        services = true
        tryNavigateMenu()
    }

    private fun tryNavigateMenu() {
        if (login && services)
            navigator.initHome(activity as BaseActivity)
    }

    private fun initHandler(time: Int) {
        Handler().postDelayed({
            //loadService()
            navigator.initHome(activity as BaseActivity)
        }, time.toLong())
    }
}