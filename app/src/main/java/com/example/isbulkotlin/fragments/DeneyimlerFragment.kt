package com.example.isbulkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isbulkotlin.adapters.DeneyimAdapter
import com.example.isbulkotlin.databinding.DeneyimEkleBinding
import com.example.isbulkotlin.databinding.FragmentDeneyimlerBinding
import com.example.isbulkotlin.model.Deneyim
import com.example.isbulkotlin.model.DeneyimCevap
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.servis.IsDao
import com.example.isbulkotlin.utils.SharedPref
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback

class DeneyimlerFragment : Fragment() {
    private var _binding:FragmentDeneyimlerBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    private lateinit var user_id:String
    private lateinit var deneyimlerList: ArrayList<Deneyim>
    private lateinit var adapter:DeneyimAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentDeneyimlerBinding.inflate(inflater,container,false)
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
            rvDeneyimler.layoutManager=LinearLayoutManager(context)
            rvDeneyimler.setHasFixedSize(true)
            fabDeneyim.setOnClickListener {
                alertOpen(it)
            }
        }
        deneyimGetir(user_id.toInt())
    }

    private fun deneyimGetir(id:Int){
        binding.progressDeneyimler.visibility=View.INVISIBLE
        deneyimlerList=ArrayList()
        dao.deneyimler(id).enqueue(object :Callback<DeneyimCevap>{
            override fun onResponse(call: Call<DeneyimCevap>, response: retrofit2.Response<DeneyimCevap>) {
                if (response.isSuccessful){
                    if (response.body()!!.tf){
                        val list=response.body()!!.deneyimler
                        list.let {
                            deneyimlerList=list as ArrayList<Deneyim>
                        }
                        binding.rvDeneyimler.visibility=View.VISIBLE
                        binding.imageDeneyimNot.visibility=View.GONE
                        adapter= DeneyimAdapter(requireContext(),deneyimlerList)
                        binding.rvDeneyimler.adapter=adapter
                        binding.progressDeneyimler.visibility=View.GONE
                    }else{
                        binding.imageDeneyimNot.visibility=View.VISIBLE
                        binding.rvDeneyimler.visibility=View.GONE
                        Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<DeneyimCevap>, t: Throwable) {
                Toast.makeText(context,"Hata",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun alertOpen(view:View){
        val bindAlert = DeneyimEkleBinding.inflate(layoutInflater)
        val alertDialog=AlertDialog.Builder(requireActivity())
        alertDialog.setTitle("İş bilgileri ekle")
        alertDialog.setMessage("Bilgilerinizi giriniz")
        alertDialog.setView(bindAlert.root)

        alertDialog.setPositiveButton("Kaydet"){ _, _ ->
            bindAlert.apply {
                val ad=editAlertDeneyimAd.text.toString().trim()
                val pozisyon=editAlertPozisyon.text.toString().trim()
                val sure=editAlertTecrube.text.toString().trim()
                if (ad.isNotEmpty() && pozisyon.isNotEmpty() && sure.isNotEmpty()){
                    deneyimEkle(user_id.toInt(),ad,pozisyon,sure)
                }else{
                    Snackbar.make(view,"Tüm alanları doldurunuz",Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        alertDialog.setNegativeButton("İptal"){_, _->

        }

        alertDialog.create().show()
    }

    private fun deneyimEkle(id:Int,sirket:String,poz:String,sure:String){
        dao.deneyimEkle(id, sirket, poz, sure).enqueue(object :Callback<Cevap>{
            override fun onResponse(call: Call<Cevap>, cevap: retrofit2.Response<Cevap>) {
                if (cevap.isSuccessful){
                    if (cevap.body()!!.tf){
                        Toast.makeText(context,cevap.body()!!.text,Toast.LENGTH_SHORT).show()
                        deneyimGetir(user_id.toInt())
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