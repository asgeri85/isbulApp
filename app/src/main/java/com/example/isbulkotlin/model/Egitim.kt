package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Egitim(

	@field:SerializedName("user_id")
	@Expose
	val userId: String? = null,

	@field:SerializedName("okul")
	@Expose
	val okul: String? = null,

	@field:SerializedName("baslangic_yil")
	@Expose
	val baslangicYil: String? = null,

	@field:SerializedName("egitim_id")
	@Expose
	val egitimId: String? = null,

	@field:SerializedName("bitis_yil")
	@Expose
	val bitisYil: String? = null,

	@field:SerializedName("bolum")
	@Expose
	val bolum: String? = null
)
