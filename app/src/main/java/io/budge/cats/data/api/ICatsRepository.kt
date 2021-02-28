package io.budge.cats.data.api

import io.budge.cats.data.model.CatBreed
import io.budge.cats.data.utils.Result


interface ICatsRepository {
    suspend fun getBreeds(): Result<MutableList<CatBreed>>
}