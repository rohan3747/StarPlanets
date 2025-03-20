package dev.rohang.starwars.presentation

import dev.rohang.starwars.domain.model.Planet

data class PlanetListState(
    val planets: List<Planet> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
