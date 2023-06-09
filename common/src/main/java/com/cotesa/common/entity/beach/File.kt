package com.cotesa.common.entity.beach

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class File {
    @SerializedName("target_id")
    @Expose
    var targetId: Int? = null

    @SerializedName("display")
    @Expose
    var display: Boolean? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("target_type")
    @Expose
    var targetType: String? = null

    @SerializedName("target_uuid")
    @Expose
    var targetUuid: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}