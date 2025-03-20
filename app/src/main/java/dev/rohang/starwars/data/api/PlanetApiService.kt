package dev.rohang.starwars.data.api

import dev.rohang.starwars.data.remote.PlanetResponse
import retrofit2.Response
import retrofit2.http.GET

interface PlanetApiService {

    @GET("planets")
    suspend fun getPlanets(): Response<PlanetResponse>
}