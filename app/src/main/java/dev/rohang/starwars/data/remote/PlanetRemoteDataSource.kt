package dev.rohang.starwars.data.remote

import dev.rohang.starwars.data.api.PlanetApiService
import dev.rohang.starwars.domain.model.Planet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

// This class is responsible for handling the communication with the remote API
class PlanetRemoteDataSource @Inject constructor(
    private val planetApiService: PlanetApiService
) {
    fun getPlanets(): Flow<List<Planet>> = flow {
        val response = planetApiService.getPlanets()
        if (response.isSuccessful) {
            val planets = response.body()?.results?.mapIndexed { index, planet ->
                Planet(
                    id = index + 100, // Assign ID from 100 onward
                    name = planet.name,
                    climate = planet.climate,
                    orbital_period = planet.orbital_period,
                    gravity = if (planet.gravity == "N/A") "Unknown" else planet.gravity // Check for "N/A"
                )
            } ?: emptyList()
            emit(planets) // Emit the list of planets
        } else {
            throw Exception("Error fetching planets: ${response.code()}")
        }
    }.flowOn(Dispatchers.IO) // Ensure network calls run on IO dispatcher
}
