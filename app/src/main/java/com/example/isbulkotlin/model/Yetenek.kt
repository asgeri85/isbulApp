package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Yetenek(
    @SerializedName("yetenek_ad")
    @Expose
    var yetenek_ad:String,

    @SerializedName("yetenek_id")
    @Expose
    var yetenek_id:Int,

    @SerializedName("yetenek_sayi")
    @Expose
    var yetenek_sayi:String
)