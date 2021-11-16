package com.example.isbulkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isbulkotlin.R
import com.example.isbulkotlin.adapters.MyWorkAdapter
import com.example.isbulkotlin.adapters.WorkAdapter
import com.example.isbulkotlin.databinding.FragmentAdvertBinding
import com.example.isbulkotlin.model.Work
import com.example.isbulkotlin.model.WorkCevap
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.servis.IsDao
import com.example.isbulkotlin.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdvertFragment : Fragment() {
    private var _binding:FragmentAdvertBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    private lateinit var user_id:String
    private lateinit var workList:ArrayList<Work>
    private lateinit var adapter:MyWorkAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentAdvertBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao=ApiUtils.getDao()
        user_id=SharedPref(requireContext()).getSession().getString("id",null).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvAdvert.layoutManager=LinearLayoutManager(context)
            rvAdvert.setHasFixedSize(true)
        }
        benimIlan(user_id.toInt())
        binding.fabAdvert.setOnClickListener {
            findNavController().navigate(AdvertFragmentDirections.actionAdvertFragmentToAddilanFragment())
        }
    }

    fun benimIlan(id:Int){
        binding.progressAdvert.visibility=View.VISIBLE
        workList= ArrayList()
        dao.benimIlan(id).enqueue(object :Callback<WorkCevap>{
            override fun onResponse(call: Call<WorkCevap>, response: Response<WorkCevap>) {
                if(response.isSuccessful){
                    if (response.body()!!.tf){
                        val list=response.body()?.ilanlar
                        list.let {
                            workList=it as ArrayList<Work>
                            adapter= MyWorkAdapter(context!!,workList)
                            binding.rvAdvert.adapter=adapter
                            binding.progressAdvert.visibility=View.GONE
                        }
                    }else{
                        binding.progressAdvert.visibility=View.GONE
                        Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<WorkCevap>, t: Throwable) {
                Toast.makeText(context,"Hata",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}