package com.cotesa.common.entity.aemet

import com.cotesa.common.entity.aemet.AemetDay
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AemetPrediction {
    @SerializedName("dia")
    @Expose
    var dia: List<AemetDay>? = null
}