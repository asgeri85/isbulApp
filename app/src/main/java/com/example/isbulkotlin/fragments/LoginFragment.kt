package com.example.isbulkotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.isbulkotlin.activities.MainActivity
import com.example.isbulkotlin.databinding.FragmentLoginBinding
import com.example.isbulkotlin.servis.ApiUtils
import com.example.isbulkotlin.model.LoginCevap
import com.example.isbulkotlin.servis.IsDao
import com.example.isbulkotlin.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!
    private lateinit var dao:IsDao
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao= ApiUtils.getDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }

            buttonLogin.setOnClickListener {
                val name=editLoginMail.text.toString().trim()
                val pass=editLoginPass.text.toString().trim()
                if (name.isNotEmpty() && pass.isNotEmpty()){
                    login(name,pass)
                }else{
                    Toast.makeText(context,"Tüm alanları dolrunuz",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun login(email:String,pass:String){
        dao.loginUser(email,pass).enqueue(object :Callback<LoginCevap>{
            override fun onResponse(call: Call<LoginCevap>, response: Response<LoginCevap>) {
                if (response.isSuccessful){
                    if (response.body()!!.tf){
                        val gsp=SharedPref(context!!)
                        response.body()?.user?.apply {
                            gsp.setSession(id.toString(),username,mail)
                        }
                        startActivity(Intent(requireActivity(),MainActivity::class.java))
                        requireActivity().finish()
                        Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,response.body()!!.text,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginCevap>, t: Throwable) {
                Toast.makeText(context,"bağlantı hatası",Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}