package io.budge.cats.data.api

import io.budge.cats.data.model.CatBreed
import io.budge.cats.data.utils.Result
import io.budge.cats.data.utils.getAPIResult
import io.budge.cats.utils.Constants.GENERIC_ERROR_CODE
import io.budge.cats.utils.Constants.GENERIC_ERROR_MESSAGE
import javax.inject.Inject

class CatsRepository @Inject constructor(private val apiService: CatsApiService) : ICatsRepository {

    override suspend fun getBreeds(): Result<MutableList<CatBreed>> {
        return try {
            getAPIResult(apiService.getCatBreeds())
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
        }
    }
}