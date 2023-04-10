package com.cotesa.murcia.feature.home.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import com.cotesa.appcore.extension.inTransaction
import com.cotesa.appcore.extension.invisible
import com.cotesa.appcore.extension.viewModel
import com.cotesa.appcore.extension.visible
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.appcore.platform.ConfigureActionBar
import com.cotesa.common.entity.common.BeachActionBar
import com.cotesa.common.util.OrderState
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.databinding.ActivityHomeBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.navigator.Navigator
import com.cotesa.murcia.R
import com.cotesa.murcia.feature.home.fragment.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    var navigator: Navigator = Navigator()

    companion object {
        fun callingIntent(context: AppCompatActivity) = Intent(context, HomeActivity::class.java)

        fun callingIntent(context: AppCompatActivity, menu: Int): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra("INTENT_EXTRA_PARAM_HOME", menu)
            if (context.intent.action != null && !context.intent.action.equals("android.intent.action.MAIN")) {
                intent.putExtra("push", context.intent.action)
            }
            return intent
        }
    }

    override fun fragment() = HomeFragment()

    override fun toggleSpecialView() {
        //
    }

    override fun subscribeToppic(toppic: String, subscribe: Boolean) {
        //
    }

    override fun navigatorSpecial(args: Bundle) {
        //
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as BeachApplication).appComponent
    }
    lateinit var binding: ActivityHomeBinding
    override fun bindingView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        homeViewModel = viewModel(viewModelFactory) {
        }
        CoroutineScope(Dispatchers.Default).launch {
            homeViewModel.callBeaches(this@HomeActivity)
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.bnvNav.apply {
            selectedItemId = com.cotesa.common.R.id.mi_home
            setOnItemSelectedListener {
                    when(it.itemId){

                        //List
                        com.cotesa.common.R.id.mi_list ->
                        {
                            navigator.initList(this@HomeActivity)
                            Log.e("Stack","${binding.fragmentContainer.size}")
                            true
                        }

                        //Map
                        com.cotesa.common.R.id.mi_map ->
                        {
                            navigator.initMap(this@HomeActivity)
                            Log.e("Stack","${binding.fragmentContainer.size}")
                            true
                        }

                        //Home
                        else->
                        {
                            navigator.initHome(this@HomeActivity)
                            Log.e("Stack","${binding.fragmentContainer.size}")
                            true

                        }

                    }

                }
        }
    }

    override fun initViews() {

    }

    override fun addFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(R.id.fragment_container, fragment()!!)
        }
    }

    override fun changeFragment(fragment: BaseFragment) = supportFragmentManager.inTransaction {
        Log.e("HomeActivity","Cambiando a ${fragment::class.java.name}")
        setReorderingAllowed(true)
        replace(
            R.id.fragment_container,
            fragment
        ).addToBackStack(null)
    }

    /**
     * Configures the activity ActionBar, setting the visibilities and functionalities of it
     *
     * @param configureActionBar The [ConfigureActionBar] for setting parameters
     */
    override fun configureActionBar(configureActionBar: ConfigureActionBar) {
        val actionBar : BeachActionBar = configureActionBar as BeachActionBar

        // Title
        binding.titleBar.text = actionBar.title

        // Back button
        if (actionBar.haveBack)
            binding.back.visible()
        else
            binding.back.invisible()

        // Search button
        if (actionBar.haveSearch) {
            binding.ivSearch.visible()
            //TODO: Implemnt search function
        }
        else
            binding.ivSearch.invisible()




    }


    private var orderState: OrderState = OrderState.ALPHABETICAL


    fun setTitle(title: String) {
        binding.titleBar.text = title
        binding.titleBar.visibility = View.VISIBLE
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in 1 until permissions.size) {
            Log.d(
                "PERMISION_HOME",
                "onRequestPermissionResult for " + permissions[i] + ":" + grantResults[i]
            )
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                //check if user select "never ask again" when denying any permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    !shouldShowRequestPermissionRationale(permissions[i])
                } else {
                    //
                }
            }
        }
    }
}
