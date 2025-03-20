package dev.rohang.starwars.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.rohang.starwars.presentation.navigation.AppNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Show the splash screen until the app is ready
        installSplashScreen().apply {
            setKeepOnScreenCondition { isLoading }
        }

        setContent {
            val navController = rememberNavController()
            val viewModel: PlanetViewModel = hiltViewModel() // Inject ViewModel

            AppNavigation(navController, viewModel) // Pass ViewModel to navigation
        }
    }

    // Simulate loading condition (Replace with actual logic)
    private val isLoading: Boolean
        get() = false // Set this based on real data loading logic
}