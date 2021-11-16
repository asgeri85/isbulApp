package com.example.isbulkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isbulkotlin.adapters.EgitimAdapter
import com.example.isbulkotlin.databinding.EgitimEkleBinding
import com.example.isbulkotlin.databinding.FragmentEgitimlerBinding
import com.example.isbulkotlin.model.Egitim
import com.example.isbulkotlin.model.EgitimCevap
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.servis.IsDao
import com.example.isbulkotlin.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback

class EgitimlerFragment : Fragment() {
    private var _binding:FragmentEgitimlerBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    private lateinit var user_id:String
    private lateinit var adapter:EgitimAdapter
    private lateinit var egitimList:ArrayList<Egitim>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentEgitimlerBinding.inflate(inflater,container,false)
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
            rvEgitimler.layoutManager=LinearLayoutManager(context)
            rvEgitimler.setHasFixedSize(true)
            fabEgitimEkle.setOnClickListener {
                alertEgitim()
            }
            egitimGetir(user_id.toInt())
        }
    }

    private fun egitimGetir(id:Int){
        binding.progressegitim.visibility=View.VISIBLE
        egitimList= ArrayList()
        dao.tumEgitim(id).enqueue(object :Callback<EgitimCevap>{
            override fun onResponse(call: Call<EgitimCevap>, response: retrofit2.Response<EgitimCevap>) {
                if (response.isSuccessful){
                    if(response.body()!!.tf){
                        val list=response.body()?.egitimler
                        list.let {
                            egitimList=it as ArrayList<Egitim>
                        }
                        adapter= EgitimAdapter(context!!,egitimList)
                        binding.rvEgitimler.adapter=adapter
                        binding.progressegitim.visibility=View.GONE
                    }else{
                        binding.progressegitim.visibility=View.GONE
                        Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<EgitimCevap>, t: Throwable) {
                Toast.makeText(context,"hata",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun alertEgitim(){
        val tasarim=EgitimEkleBinding.inflate(layoutInflater)
        val alertDialog=AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Eğitim Bilgileri")
        alertDialog.setView(tasarim.root)

        alertDialog.setPositiveButton("Ekle"){d,i->
            tasarim.apply {
                val okul=editAlertEgitimAd.text.toString().trim()
                val bolum=editAlertEgitimBolum.text.toString().trim()
                val baslangic=editAlertEgitimbaslangic.text.toString().trim()
                val bitis=editAlertEgitimBitis.text.toString().trim()
                if (okul.isNotEmpty() && bolum.isNotEmpty() && baslangic.isNotEmpty() && bitis.isNotEmpty()){
                    egitimEkle(user_id.toInt(),okul,bolum,baslangic,bitis)
                }else{
                    Toast.makeText(context,"Tüm alanları doldurunuz",Toast.LENGTH_SHORT).show()
                }
            }
        }

        alertDialog.setNegativeButton("İptal"){_,_->
        }
        alertDialog.create().show()
    }

    private fun egitimEkle(user:Int,okul:String,bolum:String,bas:String,bir:String){
        dao.egitimEkle(user,okul,bolum,bas,bir).enqueue(object :Callback<Cevap>{
            override fun onResponse(call: Call<Cevap>, cevap: retrofit2.Response<Cevap>) {
                if (cevap.isSuccessful){
                    if (cevap.body()!!.tf){
                        egitimGetir(user_id.toInt())
                        Toast.makeText(context,cevap.body()!!.text,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,cevap.body()!!.text,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Cevap>, t: Throwable) {
                Toast.makeText(context,"Hata",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}