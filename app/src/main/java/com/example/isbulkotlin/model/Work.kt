package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Work(
  @SerializedName("ilan_id")
  @Expose
  var ilan_id:Int,

  @SerializedName("ilan_sahip_id")
  @Expose
  var ilan_sahip_id:Int,

  @SerializedName("baslik")
  @Expose
  var baslik:String,

  @SerializedName("aciklama")
  @Expose
  var aciklama:String,

  @SerializedName("konum")
  @Expose
  var konum:String,

  @SerializedName("pozisyon")
  @Expose
  var pozisyon:String,

  @SerializedName("tecrube")
  @Expose
  var tecrube:String,

  @SerializedName("ilan_sirket_id")
  @Expose
  var ilan_sirket_id:Int,

  @SerializedName("sirket_ad")
  @Expose
  var sirket_ad:String,

  @SerializedName("sirket_sektor")
  @Expose
  var sirket_sektor:String)