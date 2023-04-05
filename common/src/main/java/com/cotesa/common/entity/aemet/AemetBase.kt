package com.cotesa.common.entity.aemet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AemetBase {

    @SerializedName("datos")
    @Expose
    var data : String? = null

}