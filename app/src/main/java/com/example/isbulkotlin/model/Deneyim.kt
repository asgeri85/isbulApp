package com.example.isbulkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Deneyim(

	@field:SerializedName("sure")
	@Expose
	val sure: String? = null,

	@field:SerializedName("user_id")
	@Expose
	val userId: String? = null,

	@field:SerializedName("pozisyon")
	@Expose
	val pozisyon: String? = null,

	@field:SerializedName("deneyim_id")
	@Expose
	val deneyimId: String? = null,

	@field:SerializedName("sirket_ad")
	@Expose
	val sirketAd: String? = null
)
