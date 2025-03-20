package dev.rohang.starwars.data.repository

import dev.rohang.starwars.data.local.PlanetLocalDataSource
import dev.rohang.starwars.data.remote.PlanetRemoteDataSource
import dev.rohang.starwars.domain.model.Planet
import dev.rohang.starwars.data.repository.PlanetRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class PlanetRepositoryImplTest {

    private lateinit var planetRepository: PlanetRepositoryImpl
    private val remoteDataSource: PlanetRemoteDataSource = mock()
    private val planetLocalDataSource: PlanetLocalDataSource = mock()

    @Before
    fun setUp() {
        whenever(remoteDataSource.getPlanets()).thenReturn(flowOf(emptyList())) // Prevent NotImplementedError
        planetRepository = PlanetRepositoryImpl(remoteDataSource, planetLocalDataSource)
    }

    @Test
    fun `test getPlanets returns expected list of planets`() = runBlocking {
        // Prepare mock data
        val remotePlanets = listOf(
            Planet(id = 100, name = "Tatooine", climate = "Desert", orbital_period = "365", gravity = "1"),
            Planet(id = 101, name = "Hoth", climate = "Ice", orbital_period = "500", gravity = "1")
        )

        // Mock the remote data source to return a flow of the expected planets
        whenever(remoteDataSource.getPlanets()).thenReturn(flowOf(remotePlanets))

        // Call the repository method and collect the results
        val planets = planetRepository.fetchPlanets().first()

        // Assert that the expected planets match the retrieved ones
        assertEquals(remotePlanets, planets)
    }
}
