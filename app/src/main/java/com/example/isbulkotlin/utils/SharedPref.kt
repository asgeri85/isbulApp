package com.example.isbulkotlin.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref(var context: Context) {
    private var sp: SharedPreferences = context.getSharedPreferences("oturum",0)

    fun setSession(id: String?, name:String?, mail:String?){
        val editor:SharedPreferences.Editor=sp.edit()
        editor.putString("id",id)
        editor.putString("name",name)
        editor.putString("mail",mail)
        editor.commit()
    }

    fun getSession():SharedPreferences{
        return sp
    }
}