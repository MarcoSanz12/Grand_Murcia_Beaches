package com.cotesa.murcia.feature.splash.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.cotesa.appcore.databinding.ActivityLayoutBinding
import com.cotesa.appcore.extension.inTransaction
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.murcia.R
import com.cotesa.murcia.feature.splash.fragment.SplashFragment
import java.util.*

class SplashActivity : BaseActivity() {
    companion object {
        fun callingIntent(activity: AppCompatActivity) =
            Intent(activity, SplashActivity::class.java)
    }
    lateinit var binding: ActivityLayoutBinding

    override fun bindingView() {
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun initViews() {
    }

    override fun addFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(R.id.fragment_container, fragment()!!)
        }
    }

    override fun addFragment(fragment: BaseFragment) {

    }


    override fun changeFragment(fragment: BaseFragment) = supportFragmentManager.inTransaction {
        setReorderingAllowed(true)
        replace(
            R.id.fragment_container,
            fragment
        ).addToBackStack(null)
    }



    override fun fragment() = SplashFragment()
    override fun navigatorSpecial(args: Bundle) {
    }

    override fun toggleSpecialView() {
    }

    override fun subscribeToppic(toppic: String, subscribe: Boolean) {
    }

}