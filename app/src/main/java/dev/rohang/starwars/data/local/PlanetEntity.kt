package dev.rohang.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.rohang.starwars.domain.model.Planet

@Entity(tableName = "planets")
data class PlanetEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val climate: String,
    val orbital_period: String,
    val gravity: String
)

fun PlanetEntity.toDomain(): Planet {
    return Planet(
        id = this.id,
        name = this.name,
        climate = this.climate,
        orbital_period = this.orbital_period,
        gravity = this.gravity
    )
}