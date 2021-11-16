package com.example.isbulkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.isbulkotlin.databinding.FragmentBilgiBinding


class BilgiFragment : Fragment() {
    private var _binding:FragmentBilgiBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentBilgiBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {



            imageButtonCardDeneyim.setOnClickListener {
                findNavController().navigate(BilgiFragmentDirections.actionBilgiFragmentToDeneyimlerFragment())
            }

            imageButontoEgitim.setOnClickListener {
                findNavController().navigate(BilgiFragmentDirections.actionBilgiFragmentToEgitimlerFragment())
            }

            imageButtonCardYetenek.setOnClickListener {
                findNavController().navigate(BilgiFragmentDirections.actionBilgiFragmentToYetenekFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}