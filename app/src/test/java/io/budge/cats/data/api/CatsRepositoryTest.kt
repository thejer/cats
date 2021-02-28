package io.budge.cats.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.budge.cats.data.model.CatBreed
import io.budge.cats.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class CatsRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: CatsRepository

    @Mock
    private lateinit var mockService: CatsApiService

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
        repository = CatsRepository(mockService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `getCatBreeds onSuccess returnCatBreeds`() {
        runBlocking {
            val catBreed = mutableListOf<CatBreed>()
            val result = Result.Success(catBreed)
            val response = Response.success(catBreed)
            Mockito.`when`(mockService.getCatBreeds()).thenReturn(response)
            assert(result == repository.getBreeds())
        }
    }

    @Test
    fun `getCatBreeds onFailure returnError`() {
        runBlocking {
            val result = Result.Error(
                "-1",
                "An error occurred, Please try again"
            )
            Mockito.`when`(mockService.getCatBreeds()).thenReturn(null)
            assert(result == repository.getBreeds())

        }
    }


}