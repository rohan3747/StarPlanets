package dev.rohang.starwars.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.rohang.starwars.domain.model.Planet
import dev.rohang.starwars.presentation.planet.PlanetDetailScreen
import dev.rohang.starwars.presentation.planet.PlanetListScreen
import dev.rohang.starwars.presentation.PlanetViewModel
import dev.rohang.starwars.presentation.SplashScreen

sealed class Screen(val route: String) {
    object PlanetList : Screen("planet_list")
    object PlanetDetail : Screen("planet_detail/{id}/{planetName}/{orbital_period}/{gravity}") {
        fun createRoute(planet: Planet) =
            "planet_detail/${planet.id}/${planet.name}/${planet.orbital_period}/${planet.gravity}"
    }
}

@Composable
fun AppNavigation(navController: NavHostController, viewModel: PlanetViewModel) {
    NavHost(navController = navController, startDestination = Screen.PlanetList.route) {

        composable("splash") { SplashScreen(navController) }

        composable(Screen.PlanetList.route) {
            PlanetListScreen(navController, viewModel)
        }
        composable(Screen.PlanetDetail.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            val planetName = backStackEntry.arguments?.getString("planetName") ?: ""
            val orbitalPeriod = backStackEntry.arguments?.getString("orbital_period") ?: ""
            val gravity = backStackEntry.arguments?.getString("gravity") ?: ""
            PlanetDetailScreen(navController, id, planetName, orbitalPeriod, gravity)
        }
    }
}


