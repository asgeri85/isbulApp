package com.example.isbulkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.isbulkotlin.R
import com.example.isbulkotlin.databinding.FragmentAddilanBinding
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.servis.IsDao
import com.example.isbulkotlin.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddilanFragment : Fragment() {
    private var _binding:FragmentAddilanBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    private lateinit var user_id:String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentAddilanBinding.inflate(layoutInflater,container,false)
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
            buttonIlanEkle.setOnClickListener {
                val ad=editIlanAd.text.toString().trim()
                val acik=editIlanAciklama.text.toString().trim()
                val konum=editIlanKonum.text.toString().trim()
                val poz=editIlanPoz.text.toString().trim()
                val tec=editIlanTecrube.text.toString().trim()
                val sirket=editSirketAd.text.toString().trim()
                val sektor=editSirketSektor.text.toString().trim()
                if (ad.isNotEmpty() && acik.isNotEmpty() && konum.isNotEmpty() && poz.isNotEmpty() && tec.isNotEmpty() &&
                    sirket.isNotEmpty() && sektor.isNotEmpty()){
                    ilanEkle(user_id.toInt(),ad,acik,konum,poz, tec, sirket, sektor)
                }else{
                    Toast.makeText(context,"Tüm alanları doldurunuz",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun ilanEkle(id:Int,baslik:String,aciklama:String,konum:String,poz:String,tec:String,sirket:String,sektor:String){
        dao.ilanEkle(id, baslik, aciklama, konum, poz, tec, sirket, sektor).enqueue(object :Callback<Cevap>{
            override fun onResponse(call: Call<Cevap>, response: Response<Cevap>) {
                if (response.isSuccessful){
                    if (response.body()!!.tf){
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

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}