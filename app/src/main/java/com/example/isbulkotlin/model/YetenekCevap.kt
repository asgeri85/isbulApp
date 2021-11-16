package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YetenekCevap(
    @SerializedName("yetenekler")
    @Expose
    var yetenekler:List<Yetenek>,

    @SerializedName("tf")
    @Expose
    var tf:Boolean,

    @SerializedName("text")
    @Expose
    var text:String
)