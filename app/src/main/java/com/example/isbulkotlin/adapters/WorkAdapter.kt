package com.example.isbulkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.isbulkotlin.R
import com.example.isbulkotlin.databinding.CardWorkBinding
import com.example.isbulkotlin.fragments.HomeFragmentDirections
import com.example.isbulkotlin.model.Work

class WorkAdapter(var mContext: Context,var workler:ArrayList<Work>):RecyclerView.Adapter<WorkAdapter.WorkHolder>() {
    inner class WorkHolder(var cardWorkBinding: CardWorkBinding):RecyclerView.ViewHolder(cardWorkBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkHolder {
        val tasarim=DataBindingUtil.inflate<CardWorkBinding>(LayoutInflater.from(mContext), R.layout.card_work,parent,false)
        return WorkHolder(tasarim)
    }

    override fun onBindViewHolder(holder: WorkHolder, position: Int) {
        val work=workler[position]
        holder.cardWorkBinding.work=work
        holder.cardWorkBinding.buttonCardWork.setOnClickListener {
            val action=HomeFragmentDirections.actionHomeFragmentToWorkFragment(work.ilan_id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return workler.size
    }
}