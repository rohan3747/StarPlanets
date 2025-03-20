package dev.rohang.starwars

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlanetApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // You can initialize other things here if needed
    }
}