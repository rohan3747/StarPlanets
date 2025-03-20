package dev.rohang.starwars.domain.model

import dev.rohang.starwars.data.local.PlanetEntity

data class Planet(
    val id: Int,   // Added ID field
    val name: String,
    val climate: String,
    val orbital_period: String,
    val gravity: String
)

fun Planet.toEntity(): PlanetEntity {
    return PlanetEntity(
        id = this.id,  // Assign the ID when mapping to DB entity
        name = this.name,
        climate = this.climate,
        orbital_period = this.orbital_period,
        gravity = this.gravity
    )
}
