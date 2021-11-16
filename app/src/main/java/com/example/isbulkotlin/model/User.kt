package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    @Expose
    var id:Int,

    @SerializedName("mail")
    @Expose
    var mail:String,

    @SerializedName("username")
    @Expose
    var username:String,

    @SerializedName("password")
    @Expose
    var password:String,

    @SerializedName("dogrulama_kodu")
    @Expose
    var dogrulama_kodu:String,

    @SerializedName("durum")
    @Expose
    var durum:Int
)
