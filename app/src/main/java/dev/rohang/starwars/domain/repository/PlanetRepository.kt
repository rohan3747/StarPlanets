package dev.rohang.starwars.domain.repository

import dev.rohang.starwars.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    fun fetchPlanets(): Flow<List<Planet>>
}