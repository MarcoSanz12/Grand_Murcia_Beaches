package com.cotesa.common.services

import com.cotesa.common.BuildConfig
import com.cotesa.common.apis.BeachApi
import com.cotesa.common.entity.aemet.AemetBase
import com.cotesa.common.entity.aemet.AemetInfo
import com.cotesa.common.entity.beach.Beach
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeachService
@Inject constructor(private val retrofit:Retrofit)
    : BeachApi
    {

    private val beachApi by lazy{
        retrofit.create(BeachApi::class.java)
    }

        override fun getAllBeaches(): Call<List<Beach>> = beachApi.getAllBeaches()
        override fun getAemetBase(idAemet: Int, baseUrl:String): Call<AemetBase> = beachApi.getAemetBase(idAemet,baseUrl)
        override fun getAemetInfo(direction: String, baseUrl:String): Call<List<AemetInfo>> = beachApi.getAemetInfo(direction,baseUrl)
}