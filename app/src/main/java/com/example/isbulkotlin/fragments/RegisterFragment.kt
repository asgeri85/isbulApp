package com.example.isbulkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.isbulkotlin.databinding.FragmentRegisterBinding
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.model.Cevap
import com.example.isbulkotlin.servis.IsDao
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback

class RegisterFragment : Fragment() {
    private var _binding:FragmentRegisterBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao= ApiUtils.getDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonRegister.setOnClickListener {
                val name=editRegisterName.text.toString().trim()
                val mail=editRegisterMail.text.toString().trim()
                val pass=editRegisterPass.text.toString().trim()
                val pass2=editRegisterPass2.text.toString().trim()

                if (name.isNotEmpty() && mail.isNotEmpty() && pass.isNotEmpty() && pass2.isNotEmpty()){
                    if (pass == pass2){
                        register(name,mail,pass,it)
                    }else{
                        Toast.makeText(context,"Şifreler aynı değil",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Tüm alanları doldurunuz",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun register(name:String, mail:String, pass:String,view:View){
        dao.registerUser(mail,name,pass).enqueue(object :Callback<Cevap>{
            override fun onResponse(call: Call<Cevap>, cevap: retrofit2.Response<Cevap>) {
                if (cevap.isSuccessful){
                    if (cevap.body()!!.tf){
                        Toast.makeText(context,cevap.body()!!.text,Toast.LENGTH_LONG).show()
                    }else{
                        Snackbar.make(view,cevap.body()!!.text,Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Cevap>, t: Throwable) {
                Toast.makeText(context,"bağlantı hatası",Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}