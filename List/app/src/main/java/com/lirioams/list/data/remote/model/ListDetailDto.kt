package com.lirioams.list.data.remote.model

import com.google.gson.annotations.SerializedName

class ListDetailDto (
    @SerializedName("name")
    var name: String,

    @SerializedName("photoUrl")
    var image: String,

    @SerializedName("affiliation")
    var affiliation: String,

    @SerializedName("position")
    var position: String,

    @SerializedName("gender")
    var gender: String,

    @SerializedName("weapon")
    var weapon: String,

    @SerializedName("allies")
    var allies: List<String>,

    @SerializedName("enemies")
    var enemies: List<String>
)