package io.budge.cats.di

import dagger.Module
import dagger.Provides
import io.budge.cats.data.api.CatsApiService
import io.budge.cats.data.api.CatsRepository
import io.budge.cats.data.api.ICatsRepository
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideCatsRepository(catsApiService: CatsApiService): ICatsRepository {
        return CatsRepository(catsApiService)
    }
}