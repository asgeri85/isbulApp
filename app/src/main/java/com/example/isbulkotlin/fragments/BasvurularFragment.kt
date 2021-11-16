package com.example.isbulkotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isbulkotlin.R
import com.example.isbulkotlin.adapters.BasvuruAdapter
import com.example.isbulkotlin.databinding.FragmentBasvurularBinding
import com.example.isbulkotlin.model.Basvuru
import com.example.isbulkotlin.model.BasvuruCevap
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.servis.IsDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasvurularFragment : Fragment() {
    private var _binding:FragmentBasvurularBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    private lateinit var adapter:BasvuruAdapter
    private lateinit var basvuruList:ArrayList<Basvuru>
    private val navArgs:BasvurularFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentBasvurularBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao=ApiUtils.getDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvBasvuru.layoutManager=LinearLayoutManager(context)
            rvBasvuru.setHasFixedSize(true)
        }
        basvuruGetir(navArgs.ilan)
    }
    private fun basvuruGetir(id:Int){
        binding.progressBasvuru.visibility=View.VISIBLE
        basvuruList= ArrayList()
        dao.basvurular(id).enqueue(object :Callback<BasvuruCevap>{
            override fun onResponse(call: Call<BasvuruCevap>, response: Response<BasvuruCevap>) {
                if (response.isSuccessful){
                    if (response.body()!!.tf){
                        val list=response.body()?.basvurular
                        list.let {
                            basvuruList=it as ArrayList<Basvuru>
                            adapter=BasvuruAdapter(context!!,basvuruList)
                            binding.rvBasvuru.adapter=adapter
                        }
                        binding.progressBasvuru.visibility=View.GONE
                    }else{
                        binding.progressBasvuru.visibility=View.GONE
                        Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BasvuruCevap>, t: Throwable) {
                binding.progressBasvuru.visibility=View.GONE
                Toast.makeText(context,"HAta",Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}