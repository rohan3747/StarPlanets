package dev.rohang.starwars.domain.useCase

import dev.rohang.starwars.domain.model.Planet
import dev.rohang.starwars.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

interface PlanetUseCase {
    suspend fun getPlanets(): Flow<List<Planet>>
}

class PlanetUseCaseImpl @Inject constructor(
    private val repository: PlanetRepository
) : PlanetUseCase {

    override suspend fun getPlanets(): Flow<List<Planet>> {
        return repository.fetchPlanets()
            .catch { emit(emptyList()) }   // Handle errors gracefully
    }
}