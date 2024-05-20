package com.lirioams.list.data.remote.model

import com.google.gson.annotations.SerializedName

data class ListDto(
    @SerializedName("_id")
    var id: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("photoUrl")
    var image: String,

    @SerializedName("affiliation")
    var affiliation: String
)
