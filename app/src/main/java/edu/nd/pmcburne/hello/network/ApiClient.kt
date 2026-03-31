package edu.nd.pmcburne.hello.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val api: PlacemarkApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.cs.virginia.edu/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlacemarkApi::class.java)
    }
}