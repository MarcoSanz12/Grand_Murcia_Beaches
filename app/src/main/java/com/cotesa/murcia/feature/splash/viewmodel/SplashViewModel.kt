package com.cotesa.murcia.feature.splash.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cotesa.appcore.platform.BaseViewModel
import com.cotesa.common.entity.beach.Beach
import com.cotesa.murcia.feature.splash.usecase.GetBeaches
import com.cotesa.murcia.util.BeachProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel
@Inject constructor(
    val context: Context,
    val getBeaches: GetBeaches,
    val beachProvider: BeachProvider
) : BaseViewModel() {


    var loaded: MutableLiveData<Boolean> = MutableLiveData()

    fun callAllBeaches() {
        CoroutineScope(Dispatchers.Default).launch{
            getBeaches.run(GetBeaches.Params(context)).fold(
                ::handleFailure,
                ::handleLoaded
            )
        }

    }

    private fun handleLoaded(beaches: List<Beach>) {
        beachProvider.beachList = beaches
        Log.e("beaches","Cargadas [${beaches.size}] playas")
       loaded.postValue(true)
    }


//    suspend fun callAllService(lang:String) =
//        callAllService(CallAllService.Params(lang, loaded)) {
//            it.fold(
//                ::handleFailureServices,
//                ::handleAllServiceOk
//            )
//        }
//
//    private fun handleAllServiceOk(taxs: Taxonomias) {
//        taxonomias.postValue(taxs)
//    }
//    private fun handleFailureServices(failure: Failure) {
//        taxonomias.postValue(null)
//    }
}