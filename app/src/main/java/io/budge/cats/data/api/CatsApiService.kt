package io.budge.cats.data.api

import io.budge.cats.data.model.CatBreed
import retrofit2.Response
import retrofit2.http.GET

interface CatsApiService {

    @GET("/v1/breeds")
    suspend fun getCatBreeds(): Response<MutableList<CatBreed>>
}