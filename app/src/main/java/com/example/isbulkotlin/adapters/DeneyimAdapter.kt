package com.example.isbulkotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.isbulkotlin.R
import com.example.isbulkotlin.databinding.CardDeneyimBinding
import com.example.isbulkotlin.model.Deneyim
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.servis.ApiUtils
import retrofit2.Call
import retrofit2.Callback

class DeneyimAdapter(var mContext: Context,var deneyimler:ArrayList<Deneyim>):
    RecyclerView.Adapter<DeneyimAdapter.DeneyimHolder>() {
    inner class DeneyimHolder(var dataCardDeneyimBinding:CardDeneyimBinding):
        RecyclerView.ViewHolder(dataCardDeneyimBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeneyimHolder {
        val view =DataBindingUtil.inflate<CardDeneyimBinding>(LayoutInflater.from(mContext),
            R.layout.card_deneyim,parent,false)
        return DeneyimHolder(view)
    }

    override fun onBindViewHolder(holder: DeneyimHolder, position: Int) {
        holder.dataCardDeneyimBinding.deneyim=deneyimler[position]
        holder.dataCardDeneyimBinding.imageButtonDeneyimSil.setOnClickListener {
            deneyimSil(deneyimler[position].userId!!.toInt(),deneyimler[position].deneyimId!!.toInt(),position)
        }
    }

    override fun getItemCount(): Int {
        return deneyimler.size
    }

    private fun deneyimSil(user:Int,deneyim:Int,pos:Int){
        ApiUtils.getDao().deneyimSil(user, deneyim).enqueue(object :Callback<Cevap>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Cevap>, cevap: retrofit2.Response<Cevap>) {
                if (cevap.isSuccessful){
                    if (cevap.body()!!.tf){
                        deneyimler.removeAt(pos)
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