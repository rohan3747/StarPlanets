package dev.rohang.starwars.presentation.planet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailScreen(
    navController: NavController,
    planetId: Int,
    planetName: String,
    orbitalPeriod: String,
    gravity: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = planetName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PlanetDetailContent(
            planetId = planetId,
            orbitalPeriod = orbitalPeriod,
            gravity = gravity,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun PlanetDetailContent(
    planetId: Int,
    orbitalPeriod: String,
    gravity: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlanetImage(planetId = planetId)
        Spacer(modifier = Modifier.height(16.dp))
        PlanetDetailText(label = "Orbital Period", value = orbitalPeriod)
        PlanetDetailText(label = "Gravity", value = gravity)
    }
}

@Composable
fun PlanetImage(planetId: Int) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://picsum.photos/id/${planetId}/400")
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()
        ),
        contentDescription = "Planet Image",
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun PlanetDetailText(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = when (label) {
            "Orbital Period" -> MaterialTheme.typography.titleLarge
            else -> MaterialTheme.typography.bodyMedium
        }
    )
}

