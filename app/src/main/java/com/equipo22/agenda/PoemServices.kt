package com.equipo22.agenda

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PoemServices {
    @GET("api?num={number}")
    fun getNumber(@Path("number") number: String): Call<Poem>
}