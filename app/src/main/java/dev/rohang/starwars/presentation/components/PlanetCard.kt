package dev.rohang.starwars.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import dev.rohang.starwars.domain.model.Planet

@Composable
fun PlanetCard(planet: Planet, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter("https://picsum.photos/200/300"),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(text = planet.name)
            Text(text = "Climate: ${planet.climate}")
        }
    }
}
