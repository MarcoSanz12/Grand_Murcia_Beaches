package com.cotesa.appcore.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cotesa.appcore.exception.Failure

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    fun handleFailure(failure: Failure?) {
        this.failure.postValue(failure)
    }
}