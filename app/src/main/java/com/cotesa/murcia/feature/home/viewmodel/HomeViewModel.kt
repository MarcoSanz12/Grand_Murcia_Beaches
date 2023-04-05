package com.cotesa.murcia.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cotesa.appcore.platform.BaseViewModel
import com.cotesa.common.entity.beach.Beach
import com.cotesa.murcia.util.BeachProvider
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    val beachProvider: BeachProvider
) : BaseViewModel() {

    var loaded: MutableLiveData<Boolean> = MutableLiveData()
    var beachList : MutableLiveData<List<Beach>> = MutableLiveData()

    fun callBeaches(){
        //TODO: Call room xD
        Log.e("Cargando","Cargando playas boi")
        loaded.postValue(false)
        beachList.postValue(beachProvider.beachList)
        loaded.postValue(true)
    }
}