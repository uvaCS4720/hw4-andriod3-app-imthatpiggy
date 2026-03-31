package edu.nd.pmcburne.hello.network

import retrofit2.http.GET

interface PlacemarkApi {
    @GET("~wxt4gm/placemarks.json")
    suspend fun getPlacemarks(): List<Placemark>
}