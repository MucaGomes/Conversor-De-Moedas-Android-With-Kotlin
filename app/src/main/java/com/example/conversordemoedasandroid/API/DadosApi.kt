package com.example.conversordemoedasandroid.API

import com.google.gson.annotations.SerializedName

data class DadosApi(
    @SerializedName("code")
    var code : String,

    @SerializedName("codein")
    var codein : String,

    @SerializedName("high")
    var high : Double

)

