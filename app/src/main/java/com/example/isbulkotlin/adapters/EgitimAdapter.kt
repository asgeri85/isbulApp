package com.example.isbulkotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.isbulkotlin.R
import com.example.isbulkotlin.databinding.CardEgitimBinding
import com.example.isbulkotlin.model.Egitim
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.servis.ApiUtils
import retrofit2.Call
import retrofit2.Callback

class EgitimAdapter(var mContext:Context,var egitimler:ArrayList<Egitim>):RecyclerView.Adapter<EgitimAdapter.EgitimHolder>() {
    inner class EgitimHolder(var cardEgitimBinding: CardEgitimBinding):RecyclerView.ViewHolder(cardEgitimBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EgitimHolder {
        val tasarim=DataBindingUtil.inflate<CardEgitimBinding>(LayoutInflater.from(mContext),
            R.layout.card_egitim,parent,false)
        return EgitimHolder(tasarim)
    }

    override fun onBindViewHolder(holder: EgitimHolder, position: Int) {
        val egitim=egitimler[position]
        holder.cardEgitimBinding.egitim=egitim
        holder.cardEgitimBinding.imageButEgitimSil.setOnClickListener {
            egitimSil(egitim.userId!!.toInt(),egitim.egitimId!!.toInt(),position)
        }
    }

    override fun getItemCount(): Int {
        return egitimler.size
    }

    private fun egitimSil(user_id:Int,egitim_id:Int,pos:Int){
        ApiUtils.getDao().egitimSil(user_id,egitim_id).enqueue(object :Callback<Cevap>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Cevap>, cevap: retrofit2.Response<Cevap>) {
                if (cevap.isSuccessful){
                    if(cevap.body()!!.tf){
                        egitimler.removeAt(pos)
                        notifyDataSetChanged()
                        Toast.makeText(mContext,cevap.body()!!.text,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext,cevap.body()!!.text,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Cevap>, t: Throwable) {
                Toast.makeText(mContext,"Hata",Toast.LENGTH_SHORT).show()
            }
        })
    }
}