package com.cotesa.common.apis

import com.cotesa.common.BuildConfig
import com.cotesa.common.entity.aemet.AemetBase
import com.cotesa.common.entity.aemet.AemetInfo
import com.cotesa.common.entity.beach.Beach
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

internal interface BeachApi {

    companion object{
        private const val BEACHES = "beach/0?_format=json"
        private const val AEMET_BASE = "prediccion/especifica/playa/{idAemet}/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYW11ZWxzYW50b3NAZ3J1cG90ZWNvcHkuZXMiLCJqdGkiOiJkMGY2ODBmOC1lNzA1LTQyMjctODRiZC04MjJmNGE2YWJiZjQiLCJpc3MiOiJBRU1FVCIsImlhdCI6MTYyMTMyODY1NCwidXNlcklkIjoiZDBmNjgwZjgtZTcwNS00MjI3LTg0YmQtODIyZjRhNmFiYmY0Iiwicm9sZSI6IiJ9.WDuLkBpbsEpY9glb9COa6eh4zWOuvzUq4ZPade7wQoY"
        private const val AEMET_INFO = "{direction}"
    }
    @GET(BEACHES)
    fun getAllBeaches(): Call<List<Beach>>

    @GET(AEMET_BASE)
    fun getAemetBase(@Path("idAemet") idAemet : Int, @Url baseUrl : String) : Call<AemetBase>

    @GET(AEMET_INFO)
    fun getAemetInfo(@Path("direction") direction : String, @Url baseUrl : String) : Call<List<AemetInfo>>
}