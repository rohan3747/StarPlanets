package dev.rohang.starwars.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rohang.starwars.domain.model.Planet
import dev.rohang.starwars.domain.useCase.PlanetUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val planetUseCase: PlanetUseCase
) : ViewModel() {

    // Exposed StateFlow for UI
    val planetListState: StateFlow<PlanetListState> = flow {
        emit(PlanetListState(isLoading = true)) // Start with loading state

        planetUseCase.getPlanets()
            .catch { throwable ->
                emit(PlanetListState(isError = true, errorMessage = throwable.message ?: "An error occurred"))
            }
            .collect { planets ->
                if (planets.isEmpty()) {
                    emit(PlanetListState(isError = true, errorMessage = "No planets found"))
                } else {
                    emit(PlanetListState(planets = planets))
                }
            }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Cache for 5 seconds after the last subscription
            initialValue = PlanetListState(isLoading = true) // Initial state when the ViewModel is created
        )

    // StateFlow for managing the selected planet
    private val _selectedPlanet = MutableStateFlow<Planet?>(null)
    val selectedPlanet: StateFlow<Planet?> = _selectedPlanet.asStateFlow()

    // Function to update the selected planet
    fun selectPlanet(planet: Planet) {
        _selectedPlanet.value = planet
    }

    fun clearSelectedPlanet() {
        _selectedPlanet.value = null
    }
}

