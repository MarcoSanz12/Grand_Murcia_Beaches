package com.cotesa.common.entity.aemet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AemetInfo {

    @SerializedName("nombre")
    @Expose
    var nombre: String? = null

    @SerializedName("localidad")
    @Expose
    var localidad: Int? = null

    @SerializedName("prediccion")
    @Expose
    var prediccion: AemetPrediction? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null
}