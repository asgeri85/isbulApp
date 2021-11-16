package com.example.isbulkotlin.servis

class ApiUtils {
    companion object{
        const val URL="https://microwebservice.net/ecodation/ibrahim/isbulKotlin/"
        fun getDao():IsDao{
            return RetrofitClient.getRetrofit(URL).create(IsDao::class.java)
        }
    }
}