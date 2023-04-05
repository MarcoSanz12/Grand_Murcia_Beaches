package com.cotesa.murcia.feature.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cotesa.appcore.platform.BaseViewModel
import com.cotesa.common.entity.beach.Beach
import com.cotesa.murcia.feature.home.usecase.GetBeachesDB
import com.cotesa.murcia.util.BeachProvider
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    val getBeaches: GetBeachesDB
) : BaseViewModel() {

    var beachList : MutableLiveData<List<Beach>> = MutableLiveData()

    suspend fun callBeaches(context: Context){
        getBeaches.run(GetBeachesDB.Params(context)).fold(
            ::handleFailure,
            ::handleListLoaded
        )
    }

    private fun handleListLoaded(beaches: List<Beach>) = beachList.postValue(beaches)

}


