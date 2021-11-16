package com.example.isbulkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.isbulkotlin.R
import com.example.isbulkotlin.databinding.ActivityMainBinding
import com.example.isbulkotlin.utils.SharedPref

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        check()
        navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView2,navHostFragment.navController)
    }

    private fun check(){
        val sp= SharedPref(applicationContext)
        val oturum=sp.getSession()
        if (oturum.getString("name",null)==null){
            startActivity(Intent(this@MainActivity,Loginctivity::class.java))
            finish()
        }
    }
}