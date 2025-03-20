package dev.rohang.starwars.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.rohang.starwars.data.local.PlanetDao
import dev.rohang.starwars.data.local.PlanetDatabase
import dev.rohang.starwars.data.api.PlanetApiService
import dev.rohang.starwars.data.local.PlanetLocalDataSource
import dev.rohang.starwars.data.remote.PlanetRemoteDataSource
import dev.rohang.starwars.domain.repository.PlanetRepository
import dev.rohang.starwars.data.repository.PlanetRepositoryImpl
import dev.rohang.starwars.domain.useCase.PlanetUseCase
import dev.rohang.starwars.domain.useCase.PlanetUseCaseImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.dev/api/") // Base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePlanetApiService(retrofit: Retrofit): PlanetApiService =
        retrofit.create(PlanetApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlanetDatabase =
        Room.databaseBuilder(context, PlanetDatabase::class.java, "planet_db").build()

    @Provides
    fun providePlanetDao(database: PlanetDatabase): PlanetDao = database.planetDao()

    @Provides
    @Singleton
    fun providePlanetRepository(planetRemoteDataSource: PlanetRemoteDataSource,
                                planetLocalDataSource: PlanetLocalDataSource): PlanetRepository =
        PlanetRepositoryImpl(planetRemoteDataSource, planetLocalDataSource)

    @Provides
    @Singleton
    fun providePlanetUseCase(repository: PlanetRepository): PlanetUseCase =
        PlanetUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providePlanetRemoteDataSource(apiService: PlanetApiService): PlanetRemoteDataSource {
        return PlanetRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providePlanetLocalDataSource(planetDao: PlanetDao): PlanetLocalDataSource {
        return PlanetLocalDataSource(planetDao)
    }
}

