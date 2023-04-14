package com.cotesa.appcore.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cotesa.appcore.R.color
import com.cotesa.appcore.extension.appContext
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {



    abstract var level : Int


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory



//    open fun onBackPress(): Boolean {
//        return false
//    }

    override fun onSaveInstanceState(outState: Bundle) {}

    open fun onBackPressed() {
        requireActivity().onBackPressed()
    }

    fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    fun notify(viewContainer: View, @StringRes message: Int) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    fun notifyWithAction(viewContainer: View, @StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.setActionTextColor(
            ContextCompat.getColor(
                appContext,
                color.colorTextSecondary_Core
            )
        )
        snackBar.show()
    }

    fun notifyWithAction(viewContainer: View,message: String, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.setActionTextColor(
            ContextCompat.getColor(
                appContext,
                color.colorTextSecondary_Core
            )
        )
        snackBar.show()
    }

}
