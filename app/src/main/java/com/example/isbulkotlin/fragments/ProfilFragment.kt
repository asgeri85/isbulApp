package com.example.isbulkotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.isbulkotlin.activities.Loginctivity
import com.example.isbulkotlin.databinding.FragmentProfilBinding
import com.example.isbulkotlin.utils.SharedPref

class ProfilFragment : Fragment() {
    private var _binding:FragmentProfilBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentProfilBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textViewAdProfil.text=SharedPref(requireContext()).getSession().getString("name",null)
            textViewProfilPoz.text=SharedPref(requireContext()).getSession().getString("mail",null)
            imageButtonCikis.setOnClickListener {
               alertExit()
            }

            imageButtonProfil.setOnClickListener {
               findNavController().navigate(ProfilFragmentDirections.actionProfilFragmentToBilgiFragment())
            }


        }
    }

    private fun alertExit(){
        val alertDialog=AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Çıkış")
        alertDialog.setMessage("Çıkış yapmak istiyor musunuz?")

        alertDialog.setPositiveButton("Evet"){ _, _ ->
            val gsp=SharedPref(requireContext())
            gsp.setSession(null,null,null)
            startActivity(Intent(requireActivity(),Loginctivity::class.java))
            requireActivity().finish()
        }

        alertDialog.setNegativeButton("İptal"){_,_->

        }
        alertDialog.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}