package com.equipo22.agenda

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PoemServices {
    @GET("api?")
    fun getPoem(@Query("num") num : Int): Call<Poem>
}