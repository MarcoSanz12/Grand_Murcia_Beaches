package com.cotesa.murcia.feature.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cotesa.appcore.extension.notNull
import com.cotesa.appcore.platform.BaseViewModel
import com.cotesa.common.entity.aemet.AemetInfo
import com.cotesa.common.entity.beach.Beach
import com.cotesa.murcia.feature.home.usecase.GetAemetInfo
import com.cotesa.murcia.feature.home.usecase.GetBeachesDB
import com.cotesa.murcia.util.BeachProvider
import kotlinx.coroutines.selects.select
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    val getBeaches: GetBeachesDB,
    val getAemet : GetAemetInfo
) : BaseViewModel() {

    var beachList : MutableLiveData<List<Beach>> = MutableLiveData()
    var aemetInfo : MutableLiveData<AemetInfo> = MutableLiveData()
    var selectedBeach : MutableLiveData<Beach> = MutableLiveData()

    suspend fun callBeaches(context: Context){
        getBeaches.run(GetBeachesDB.Params(context)).fold(
            ::handleFailure,
            ::handleListLoaded
        )
    }

    suspend fun callAemetInfo(context: Context){
        if (selectedBeach.value != null)
            getAemet.run(GetAemetInfo.Params(selectedBeach.value!!.idAemet as Int)).fold(
                ::handleFailure,
                ::handleAemetLoaded
            )
        }

    fun selectBeach(beach:Beach) = selectedBeach.postValue(beach)
    private fun handleAemetLoaded(aemetLoaded: List<AemetInfo>) = aemetInfo.postValue(aemetLoaded.first())

    private fun handleListLoaded(beaches: List<Beach>) = beachList.postValue(beaches)

}






