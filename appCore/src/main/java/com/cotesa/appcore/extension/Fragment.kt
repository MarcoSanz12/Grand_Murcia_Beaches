package com.cotesa.appcore.extension

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.appcore.platform.BaseFragment

inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.viewModel(
    factory: Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProvider(activity!!, factory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.viewModel(factory: Factory): T {
    return ViewModelProvider(activity!!, factory)[T::class.java]
}

inline fun <reified T : ViewModel> androidx.fragment.app.FragmentActivity.viewModel(
    factory: Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProvider(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun BaseFragment.close() = fragmentManager?.popBackStack()

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

