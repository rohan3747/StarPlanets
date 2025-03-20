package dev.rohang.starwars.data.remote

import com.google.gson.annotations.SerializedName
import dev.rohang.starwars.domain.model.Planet

data class PlanetResponse(
    @SerializedName("results") val results: List<Planet>

)