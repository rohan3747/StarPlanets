package dev.rohang.starwars.domain.model

import dev.rohang.starwars.data.local.PlanetEntity
import dev.rohang.starwars.data.local.toDomain
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class PlanetTest {
    @Test
    fun `toEntity should map Planet to PlanetEntity correctly`() {
        val planet = Planet(
            id = 1,
            name = "Tatooine",
            climate = "Arid",
            orbital_period = "304",
            gravity = "1 standard"
        )

        val entity = planet.toEntity()

        assertEquals(planet.id, entity.id)
        assertEquals(planet.name, entity.name)
        assertEquals(planet.climate, entity.climate)
        assertEquals(planet.orbital_period, entity.orbital_period)
        assertEquals(planet.gravity, entity.gravity)
    }

    @Test
    fun `toDomain should map PlanetEntity to Planet correctly`() {
        val entity = PlanetEntity(
            id = 2,
            name = "Hoth",
            climate = "Frozen",
            orbital_period = "549",
            gravity = "1.1 standard"
        )

        val planet = entity.toDomain()

        assertEquals(entity.id, planet.id)
        assertEquals(entity.name, planet.name)
        assertEquals(entity.climate, planet.climate)
        assertEquals(entity.orbital_period, planet.orbital_period)
        assertEquals(entity.gravity, planet.gravity)
    }
}