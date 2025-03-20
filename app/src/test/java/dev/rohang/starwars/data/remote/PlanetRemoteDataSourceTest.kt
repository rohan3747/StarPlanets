package dev.rohang.starwars.data.remote

import dev.rohang.starwars.data.api.PlanetApiService
import dev.rohang.starwars.domain.model.Planet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PlanetRemoteDataSourceTest {

    @Mock
    private lateinit var planetApiService: PlanetApiService

    private lateinit var remoteDataSource: PlanetRemoteDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        remoteDataSource = PlanetRemoteDataSource(planetApiService)
    }

    @Test
    fun `getPlanets should return list of planets when API call is successful`() = runTest(testDispatcher) {
        val mockResponse = PlanetResponse(
            results = listOf(
                Planet(1, "Tatooine", "Arid", "304", "1 standard"),
                Planet(2, "Hoth", "Frozen", "549", "Unknown")
            )
        )
        `when`(planetApiService.getPlanets()).thenReturn(Response.success(mockResponse))

        val result = remoteDataSource.getPlanets().first()

        assertEquals(2, result.size)
        assertEquals("Tatooine", result[0].name)
        assertEquals("Arid", result[0].climate)
        assertEquals("304", result[0].orbital_period)
        assertEquals("1 standard", result[0].gravity)
        assertEquals("Hoth", result[1].name)
        assertEquals("Frozen", result[1].climate)
        assertEquals("549", result[1].orbital_period)
        assertEquals("Unknown", result[1].gravity)
    }

    @Test
    fun `getPlanets should throw exception when API call fails`() = runTest(testDispatcher) {

        assertThrows(Exception::class.java) {
            runTest { remoteDataSource.getPlanets().first() }
        }
    }

}
