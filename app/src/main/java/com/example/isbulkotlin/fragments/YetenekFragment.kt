package com.example.isbulkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isbulkotlin.adapters.YetenekAdapter
import com.example.isbulkotlin.databinding.FragmentYetenekBinding
import com.example.isbulkotlin.databinding.YetenekEkleBinding
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.model.Yetenek
import com.example.isbulkotlin.model.YetenekCevap
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.servis.IsDao
import com.example.isbulkotlin.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YetenekFragment : Fragment() {
    private var _binding:FragmentYetenekBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    private lateinit var yetenekList:ArrayList<Yetenek>
    private lateinit var adapter:YetenekAdapter
    private lateinit var user_id:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentYetenekBinding.inflate(inflater,container,false)
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
            rvYetenek.layoutManager=LinearLayoutManager(context)
            rvYetenek.setHasFixedSize(true)
            yetenekGetir(user_id.toInt())
            fabYetenekEkle.setOnClickListener {
                alertAc()
            }
        }
    }

    private fun yetenekEkle(id:Int,ad:String){
       dao.yetenekEkle(id,ad).enqueue(object:Callback<Cevap>{
           override fun onResponse(call: Call<Cevap>, response: Response<Cevap>) {
               if (response.isSuccessful){
                   if (response.body()!!.tf){
                       yetenekGetir(user_id.toInt())
                       Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                   }else{
                       Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                   }
               }
           }

           override fun onFailure(call: Call<Cevap>, t: Throwable) {
               Toast.makeText(context,"Hata",Toast.LENGTH_SHORT).show()
           }

       })
    }

    private fun yetenekGetir(id:Int){
        binding.progressBarYetenek.visibility=View.VISIBLE
        yetenekList= ArrayList()
        dao.yetenekler(id).enqueue(object :Callback<YetenekCevap>{
            override fun onResponse(call: Call<YetenekCevap>, response: Response<YetenekCevap>) {
                if (response.isSuccessful){
                    if(response.body()!!.tf){
                        val list=response.body()!!.yetenekler
                        list.let {
                            yetenekList=it as ArrayList<Yetenek>
                        }
                        adapter= YetenekAdapter(requireContext(),yetenekList)
                        binding.rvYetenek.adapter=adapter
                        binding.progressBarYetenek.visibility=View.GONE
                    }else{
                        Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                        binding.progressBarYetenek.visibility=View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<YetenekCevap>, t: Throwable) {
                Toast.makeText(context,"Hata",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun alertAc(){
        val tasarim=YetenekEkleBinding.inflate(layoutInflater)
        val alertD=AlertDialog.Builder(requireContext())
        alertD.setTitle("Yetenek Ekle")
        alertD.setMessage("Yetenek Adını giriniz")
        alertD.setView(tasarim.root)

        alertD.setPositiveButton("Ekle"){_,_->
            val yetenekAd=tasarim.editAlertyetenek.text.toString().trim()
            if (yetenekAd.isNotEmpty()){
                yetenekEkle(user_id.toInt(),yetenekAd)
            }else{
                Toast.makeText(context,"Gerekli alanı doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }

        alertD.setNegativeButton("İptal"){_,_,-> }
        alertD.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}