package com.example.isbulkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.isbulkotlin.R
import com.example.isbulkotlin.databinding.CardMyworkBinding
import com.example.isbulkotlin.fragments.AdvertFragmentDirections
import com.example.isbulkotlin.model.Work

class MyWorkAdapter(var mContext:Context,var workler:ArrayList<Work>):RecyclerView.Adapter<MyWorkAdapter.MyWorkHolder>(){
    inner class MyWorkHolder(var myworkBinding: CardMyworkBinding):RecyclerView.ViewHolder(myworkBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWorkHolder {
        val tasarim=DataBindingUtil.inflate<CardMyworkBinding>(LayoutInflater.from(mContext), R.layout.card_mywork,
                    parent,false)
        return MyWorkHolder(tasarim)
    }

    override fun onBindViewHolder(holder: MyWorkHolder, position: Int) {
        val myWork=workler[position]
        holder.myworkBinding.mywork=myWork
        holder.myworkBinding.buttonMyWork.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(AdvertFragmentDirections.actionAdvertFragmentToBasvurularFragment(myWork.ilan_id))
        }
    }

    override fun getItemCount(): Int {
        return workler.size
    }
}