package dev.rohang.starwars.presentation.planet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import dev.rohang.starwars.domain.model.Planet
import dev.rohang.starwars.presentation.PlanetViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.rohang.starwars.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListScreen(
    navController: NavController,
    viewModel: PlanetViewModel
) {
    // Collect the state from the ViewModel
    val planetListState by viewModel.planetListState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planets") }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                planetListState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                planetListState.isError -> {
                    Text(
                        text = "Error: ${planetListState.errorMessage}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                planetListState.planets.isEmpty() -> {
                    Text(
                        text = "No planets available",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(planetListState.planets) { planet ->
                            PlanetItem(planet, navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PlanetItem(planet: Planet, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                // Use createRoute to build the correct route string
                navController.navigate(Screen.PlanetDetail.createRoute(planet))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://picsum.photos/id/${planet.id}/200")
                        .diskCachePolicy(CachePolicy.ENABLED) // Enables disk caching
                        .diskCachePolicy(CachePolicy.READ_ONLY) // Ensure it reads from cache
                        .memoryCachePolicy(CachePolicy.ENABLED) // Ensure memory caching is enabled
                        .build()
                ),
                contentDescription = "Planet Image",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = planet.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "Climate: ${planet.climate}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}




