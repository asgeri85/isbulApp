package com.example.isbulkotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.isbulkotlin.R
import com.example.isbulkotlin.databinding.FragmentWorkBinding
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.model.WorkCevap
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.servis.IsDao
import com.example.isbulkotlin.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkFragment : Fragment() {
    private var _binding:FragmentWorkBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    private val args:WorkFragmentArgs by navArgs()
    private lateinit var user_id:String
    private lateinit var sahip_id:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentWorkBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao=ApiUtils.getDao()
        user_id= SharedPref(requireContext()).getSession().getString("id",null).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detayGetir(args.id)
        binding.buttonWorkBasvur.setOnClickListener {
            basvur(args.id,sahip_id.toInt(),user_id.toInt())
        }
    }

    private fun basvur(ilan:Int,sahip:Int,basvuran:Int){
        dao.ilanBasvur(ilan, sahip, basvuran).enqueue(object :Callback<Cevap>{
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

    private fun detayGetir(id:Int){
        binding.progressWork.visibility=View.VISIBLE
        dao.ilanDetay(id).enqueue(object :Callback<WorkCevap>{
            override fun onResponse(call: Call<WorkCevap>, response: Response<WorkCevap>) {
                if(response.isSuccessful){
                    if(response.body()!!.tf){
                        val work=response.body()?.ilam
                        work.let {
                            binding.apply {
                                sahip_id=it!!.ilan_sirket_id.toString()
                                textWorkBaslik.text=it.baslik
                                textWorkSehir.text=it.konum
                                textWorkPoz.text=it.pozisyon
                                textWorkTecrube.text=it.tecrube+" yıl"
                                textWorkAciklama.text=it.aciklama
                                textWorkSirket.text=it.sirket_ad
                                textWorkSektor.text=it.sirket_sektor
                            }
                        }
                        binding.progressWork.visibility=View.GONE
                    }else{
                        binding.progressWork.visibility=View.GONE
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