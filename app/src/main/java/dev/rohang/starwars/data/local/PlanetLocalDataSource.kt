package dev.rohang.starwars.data.local

import javax.inject.Inject

class PlanetLocalDataSource @Inject constructor(
    private val planetDao: PlanetDao
) {

    suspend fun getPlanets(): List<PlanetEntity> {
        // Fetch planets from the local database (Room)
        return planetDao.getAllPlanets()
    }

    suspend fun savePlanets(planets: List<PlanetEntity>) {
        // Save the planets to the local database (Room)
        planetDao.insertAll(planets)
    }
}