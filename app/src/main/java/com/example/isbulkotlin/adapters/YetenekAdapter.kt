package com.example.isbulkotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.isbulkotlin.R
import com.example.isbulkotlin.databinding.CardYetenekBinding
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.model.Yetenek
import com.example.isbulkotlin.servis.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YetenekAdapter(var mContext: Context, var yetenekler:ArrayList<Yetenek>):
    RecyclerView.Adapter<YetenekAdapter.YetenekHolder>() {
    inner class YetenekHolder(var yetenekBinding: CardYetenekBinding):RecyclerView.ViewHolder(yetenekBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YetenekHolder {
        val tasarim=DataBindingUtil.inflate<CardYetenekBinding>(LayoutInflater.from(mContext),
            R.layout.card_yetenek,parent,false)
        return YetenekHolder(tasarim)
    }

    override fun onBindViewHolder(holder: YetenekHolder, position: Int) {
        val yetenek=yetenekler[position]
        holder.yetenekBinding.yetenek=yetenek
        holder.yetenekBinding.imageButtonYetenekSil.setOnClickListener {
            yetenekSil(yetenek.yetenek_id,position)
        }
    }

    override fun getItemCount(): Int {
        return yetenekler.size
    }

    private fun yetenekSil(id:Int,pos:Int){
        ApiUtils.getDao().yetenekSil(id).enqueue(object :Callback<Cevap>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Cevap>, response: Response<Cevap>) {
                if (response.isSuccessful){
                    if (response.body()!!.tf){
                        yetenekler.removeAt(pos)
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
}