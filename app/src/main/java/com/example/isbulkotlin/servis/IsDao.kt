package com.example.isbulkotlin.servis

import com.example.isbulkotlin.model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface IsDao {
    @POST("register.php")
    @FormUrlEncoded
    fun registerUser(@Field("mail") mail:String,@Field("name") name:String,@Field("pass") pass:String):Call<Cevap>

    @POST("login.php")
    @FormUrlEncoded
    fun loginUser(@Field("mail") mail:String,@Field("pass") pass:String):Call<LoginCevap>

    @POST("deneyimEkle.php")
    @FormUrlEncoded
    fun deneyimEkle(@Field("id") id:Int,@Field("sirket") sirket:String,@Field("pozisyon") poz:String,
                    @Field("sure") sure:String):Call<Cevap>

    @POST("deneyimler.php")
    @FormUrlEncoded
    fun deneyimler(@Field("id") id:Int):Call<DeneyimCevap>

    @POST("deneyimSil.php")
    @FormUrlEncoded
    fun deneyimSil(@Field("user_id") user:Int,@Field("deneyim_id") deneyim:Int):Call<Cevap>

    @POST("egitimEkle.php")
    @FormUrlEncoded
    fun egitimEkle(@Field("user") user_id:Int,@Field("okul") okul:String,@Field("bolum") bolum:String,
                    @Field("baslangic") bas:String,@Field("bitis") bitis:String):Call<Cevap>

    @POST("egitimler.php")
    @FormUrlEncoded
    fun tumEgitim(@Field("user_id") id:Int):Call<EgitimCevap>

    @POST("egitimSil.php")
    @FormUrlEncoded
    fun egitimSil(@Field("user_id") user:Int,@Field("egitim_id") egitim:Int):Call<Cevap>

    @POST("yetenekler.php")
    @FormUrlEncoded
    fun yetenekler(@Field("id") id:Int):Call<YetenekCevap>

    @POST("yetenekEkle.php")
    @FormUrlEncoded
    fun yetenekEkle(@Field("id") id:Int,@Field("ad") ad:String):Call<Cevap>

    @POST("yetenekSil.php")
    @FormUrlEncoded
    fun yetenekSil(@Field("id") id:Int):Call<Cevap>

    @POST("ilanlar.php")
    @FormUrlEncoded
    fun ilanlar(@Field("user_id") id:Int):Call<WorkCevap>

    @POST("ilanDetay.php")
    @FormUrlEncoded
    fun ilanDetay(@Field("ilan_id") id:Int):Call<WorkCevap>

    @POST("basvur.php")
    @FormUrlEncoded
    fun ilanBasvur(@Field("ilan_id") ilan:Int,@Field("sahip_id") sahip:Int,@Field("basvuran_id") basvuran:Int):Call<Cevap>

    @POST("ilanEkle.php")
    @FormUrlEncoded
    fun ilanEkle(@Field("sahip_id") id:Int,@Field("baslik") baslik:String,@Field("aciklama") aciklama:String,
                @Field("konum") konum:String,@Field("pozisyon") poz:String,@Field("tecrube") tec:String,
                @Field("sirket") sirket:String,@Field("sektor") sektor:String):Call<Cevap>

    @POST("ilanlarim.php")
    @FormUrlEncoded
    fun benimIlan(@Field("id") id:Int):Call<WorkCevap>

    @POST("ilanBasvuru.php")
    @FormUrlEncoded
    fun basvurular(@Field("ilan_id") id:Int):Call<BasvuruCevap>

    @POST("basvuruCevap.php")
    @FormUrlEncoded
    fun basvuruCevapla(@Query("durum") durum:Int, @Query("ilan_id") id:Int, @Query("baslik") baslik:String,
                       @Field("mail") mail:String,@Field("ad") ad:String):Call<Cevap>
}