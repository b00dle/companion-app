package com.example.companionapp.data

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("sounds")
    fun fetchAllSounds(): Single<List<Sound>>

}