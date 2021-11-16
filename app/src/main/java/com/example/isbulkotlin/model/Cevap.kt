package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cevap(
    @SerializedName("tf")
    @Expose
    var tf:Boolean,

    @SerializedName("text")
    @Expose
    var text:String
)