package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WorkCevap(
    @SerializedName("ilanlar")
    @Expose
    var ilanlar:List<Work>,

    @SerializedName("ilan")
    @Expose
    var ilam:Work,

    @SerializedName("tf")
    @Expose
    var tf:Boolean,

    @SerializedName("text")
    @Expose
    var text:String
)