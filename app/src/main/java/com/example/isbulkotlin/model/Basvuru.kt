package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Basvuru(
    @SerializedName("ilan_id")
    @Expose
    val ilan_id:Int,

    @SerializedName("baslik")
    @Expose
    val baslik:String,

    @SerializedName("username")
    @Expose
    val username:String,

    @SerializedName("mail")
    @Expose
    val mail:String,

    @SerializedName("basvuru_id")
    @Expose
    val basvuru_id:Int,
)
