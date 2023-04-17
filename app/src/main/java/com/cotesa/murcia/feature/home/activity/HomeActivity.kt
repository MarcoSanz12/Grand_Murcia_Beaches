package com.cotesa.murcia.feature.home.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import com.cotesa.appcore.extension.*
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.appcore.platform.ConfigureActionBar
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.entity.common.BeachActionBar
import com.cotesa.common.util.FragmentEnum
import com.cotesa.common.util.OrderState
import com.cotesa.common.util.SettingsUtils
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.databinding.ActivityHomeBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.navigator.Navigator
import com.cotesa.murcia.R
import com.cotesa.murcia.feature.home.fragment.DetailFragment
import com.cotesa.murcia.feature.home.fragment.HomeFragment
import com.cotesa.murcia.feature.home.fragment.ListFragment
import com.cotesa.murcia.feature.home.fragment.MapFragment
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
                when (it.itemId) {
                    //List
                    com.cotesa.common.R.id.mi_list -> {
                        navigator.initList(this@HomeActivity)
                        true
                    }

                    //Map
                    com.cotesa.common.R.id.mi_map -> {
                        navigator.initMap(this@HomeActivity)
                        true
                    }

                    //Home
                    else -> {
                        navigator.initHome(this@HomeActivity)
                        true
                    }
                }
            }
        }

        supportFragmentManager.apply {
            addOnBackStackChangedListener {
                val last = supportFragmentManager.fragments.last()
                if (last is BaseFragment)
                    last.initializeView()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBackPressed() {
        val lastFragment = supportFragmentManager.fragments.last()
        when (lastFragment) {
            is DetailFragment -> {
                super.onBackPressed()
            }

            is ListFragment ->
                if (!resetSearch())
                    binding.bnvNav.selectedItemId = com.cotesa.common.R.id.mi_home

            is MapFragment ->
                binding.bnvNav.selectedItemId = com.cotesa.common.R.id.mi_home

            else ->{
                super.onBackPressed()
            }

        }
    }


    override fun addFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(R.id.fragment_container, fragment()!!)
        }
    }

    override fun addFragment(fragment: BaseFragment) {
        resetSearch()
        supportFragmentManager.inTransaction {
            addToBackStack(fragment.level.toString())
            add(R.id.fragment_container, fragment)
        }
    }

    override fun changeFragment(fragment: BaseFragment): Int {
        clearBackstack()
        resetSearch()
        val xd = supportFragmentManager.inTransaction {
            replace(
                R.id.fragment_container,
                fragment
            )
        }
        return xd
    }


    override fun initViews() {

    }

    /**
     * Configures the activity ActionBar, setting the visibilities and functionalities of it
     *
     * @param configureActionBar The [ConfigureActionBar] for setting parameters
     */
    override fun configureActionBar(configureActionBar: ConfigureActionBar) {
        val actionBar: BeachActionBar = configureActionBar as BeachActionBar

        // Title
        binding.titleBar.text = actionBar.title

        // Back button
        if (actionBar.haveBack)
            binding.back.visible()
        else
            binding.back.invisible()

        if (actionBar.haveFavorite)
            binding.ivFavorite.apply {
                visible()
                val beach = homeViewModel.selectedBeach.value as Beach
                isChecked = SettingsUtils.isInFavorites(context!!,beach)
                setOnClickListener{
                    val listFragment = getListFragment()

                    listFragment.notNull {
                        actionBar.favoriteFunction!!.invoke()
                        it.refreshRecycler()
                        isChecked = SettingsUtils.isInFavorites(context!!,beach)
                    }

                }
            }
        else
            binding.ivFavorite.invisible()

        // Search button
        if (actionBar.haveSearch) {
            binding.ivSearch.apply {
                visible()
                val searchBar = binding.clSearchBar

                setOnClickListener{
                    if (isChecked){
                        searchBar.visible()
                        binding.etSearchBar.apply {
                            requestFocus()
                            applicationContext.showKeyboard(this)
                        }
                    }
                    else{
                        searchBar.invisible()
                        applicationContext.hideKeyboard(binding.etSearchBar)
                    }


                }
            }
            binding.etSearchBar.addTextChangedListener(object:TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if (getListFragment() != null)
                        actionBar.searchFunction!!.invoke(p0.toString())
                }

            })
            binding.ibSearchBarClose.setOnClickListener{
                binding.etSearchBar.text = Editable.Factory.getInstance().newEditable("")
            }

        } else
            binding.ivSearch.invisible()
    }


    private fun getListFragment() : ListFragment?{
        return supportFragmentManager.fragments.filterIsInstance<ListFragment>().firstOrNull()
    }

    /**
     * Checks search's button toggle state, if TRUE perform a click & return true else return false
     *
     * @return [Boolean] to check if button has been pressed
     */
    private fun resetSearch() : Boolean{
        currentFocus?.let {
            baseContext.hideKeyboard(it)
        }
        binding.ivSearch.apply {
            return if (isChecked) {
                binding.ibSearchBarClose.performClick()
                performClick()
            }
            else
                false
        }
    }




    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
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
