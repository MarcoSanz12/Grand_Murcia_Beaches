package com.cotesa.appcore.platform

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.cotesa.appcore.R
import com.cotesa.appcore.databinding.ActivityLayoutBinding
import com.cotesa.appcore.extension.inTransaction
import com.cotesa.appcore.extension.notNull

/**
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity() {

//    open fun layoutId(): Int = R.layout.activity_layout
    companion object {
        var canChangeOrientation = false
    }

    //    }
    abstract fun bindingView()
/**
binding = ActivityLayoutBinding.inflate(layoutInflater)
val view = binding.root
setContentView(view)
 * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView()
        initViews()
        fragment().notNull {
            addFragment(savedInstanceState)
        }
        postCreate()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (!canChangeOrientation) {
            //android O fix bug orientation
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

    }

    fun postCreate() {
        //
    }

   abstract fun initViews()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (supportFragmentManager.fragments.last() != null)
            supportFragmentManager.fragments.last()!!
                .onActivityResult(requestCode, resultCode, data)
        else
            super.onActivityResult(requestCode, resultCode, data)
    }

    abstract fun addFragment(savedInstanceState: Bundle?)
    abstract fun changeFragment(fragment: BaseFragment): Int

    abstract fun addFragment(fragment: BaseFragment)

//     supportFragmentManager.inTransaction {
//        setReorderingAllowed(true)
//        replace(
//            R.id.fragment_container,
//            fragment
//        ).addToBackStack(null)
//    }

//    fun configureActionBar(
//        title: String,
//        haveBack: Boolean
//    ) {
//        setTitle(title)
//        setActionbarVisibility(View.VISIBLE)
//    }

    open fun configureActionBar(configureActionBar: ConfigureActionBar) {
        //
    }

    abstract fun fragment(): BaseFragment?

    private var scannedResult: String = ""

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("scannedResult", scannedResult)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.let {
            scannedResult = it.getString("scannedResult")!!
        }
    }

    fun clearBackstack(){
        supportFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }
    abstract fun navigatorSpecial(args: Bundle)
    abstract fun toggleSpecialView()
    abstract fun subscribeToppic(toppic: String, subscribe: Boolean)

}
