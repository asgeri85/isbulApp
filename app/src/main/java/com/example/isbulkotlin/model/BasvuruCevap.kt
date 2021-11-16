package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BasvuruCevap(
    @SerializedName("basvurular")
    @Expose
    var basvurular:List<Basvuru>,

    @SerializedName("tf")
    @Expose
    var tf:Boolean,

    @SerializedName("text")
    @Expose
    var text:String
)