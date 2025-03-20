package dev.rohang.starwars.data.repository

import dev.rohang.starwars.data.local.PlanetLocalDataSource
import dev.rohang.starwars.data.local.toDomain
import dev.rohang.starwars.data.remote.PlanetRemoteDataSource
import dev.rohang.starwars.domain.model.Planet
import dev.rohang.starwars.domain.model.toEntity
import dev.rohang.starwars.domain.repository.PlanetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val planetRemoteDataSource: PlanetRemoteDataSource,
    private val planetLocalDataSource: PlanetLocalDataSource
): PlanetRepository {

    override fun fetchPlanets(): Flow<List<Planet>> {
        return planetRemoteDataSource.getPlanets()
            .onEach { planets ->
                // Cache remote data in local DB for offline support
                val planetEntities = planets.map { it.toEntity() }
                planetLocalDataSource.savePlanets(planetEntities)
            }
            .catch { e ->
                // On error, try loading from local storage
                emit(planetLocalDataSource.getPlanets().map { it.toDomain() })
            }
            .flowOn(Dispatchers.IO) // Ensure execution happens in background
    }

}