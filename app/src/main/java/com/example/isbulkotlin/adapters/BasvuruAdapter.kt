package com.example.isbulkotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.isbulkotlin.databinding.CardBasvuruBinding
import com.example.isbulkotlin.model.Basvuru
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.servis.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasvuruAdapter(var mContext:Context,var basvurular:ArrayList<Basvuru>):
    RecyclerView.Adapter<BasvuruAdapter.BasvuruHolder>() {
    inner class BasvuruHolder(var cardBasvuruBinding: CardBasvuruBinding):RecyclerView.ViewHolder(cardBasvuruBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasvuruHolder {
        val tasarim=CardBasvuruBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return BasvuruHolder(tasarim)
    }

    override fun onBindViewHolder(holder: BasvuruHolder, position: Int) {
        val basvuru=basvurular[position]
        holder.cardBasvuruBinding.textBasvuranAd.text=basvuru.username
        holder.cardBasvuruBinding.imageButtonOnay.setOnClickListener {
            cevapla(basvuru.mail,basvuru.basvuru_id,1,basvuru.baslik,basvuru.username,position)
        }

        holder.cardBasvuruBinding.imageButtonRed.setOnClickListener {
            cevapla(basvuru.mail,basvuru.basvuru_id,0,basvuru.baslik,basvuru.username,position)
        }
    }

    private fun cevapla(mail:String,id:Int,durum:Int,baslik:String,ad:String,pos:Int){
        ApiUtils.getDao().basvuruCevapla(durum, id, baslik, mail, ad).enqueue(object :Callback<Cevap>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Cevap>, response: Response<Cevap>) {
                if (response.isSuccessful){
                    if (response.body()!!.tf){
                        basvurular.removeAt(pos)
                        notifyDataSetChanged()
                        Toast.makeText(mContext,response.body()!!.text,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext,response.body()!!.text,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Cevap>, t: Throwable) {
                Toast.makeText(mContext,"Hata",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return basvurular.size
    }
}