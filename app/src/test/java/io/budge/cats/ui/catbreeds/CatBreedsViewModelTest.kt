package io.budge.cats.ui.catbreeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.budge.cats.LiveDataTestUtil
import io.budge.cats.data.api.CatsRepository
import io.budge.cats.data.model.CatBreed
import io.budge.cats.data.model.Image
import io.budge.cats.data.utils.LoadingStatus
import io.budge.cats.data.utils.Result
import io.budge.cats.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CatBreedsViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: CatsRepository

    private lateinit var viewModel: CatBreedsViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
        viewModel = CatBreedsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `getCatBreeds on success catBreeds value is set`() {
        runBlocking {
            val tesCatBreeds = mutableListOf(
                CatBreed(
                    "Cat breed description",
                    "id1",
                    Image("Image url"),
                    "Cat Breed name",
                    "Cat Breed Origin",
                    "temperament1, temperament2",
                    "wikipedia url"
                )
            )
            Mockito.`when`(repository.getBreeds()).thenReturn(Result.Success(tesCatBreeds))
            viewModel.getCatBreeds()
            Assert.assertEquals(LiveDataTestUtil.getValue(viewModel.loadingStatus), LoadingStatus.Loading)
            Assert.assertEquals(LiveDataTestUtil.getValue(viewModel.catBreeds), tesCatBreeds)
            Assert.assertEquals(LiveDataTestUtil.getValue(viewModel.loadingStatus), LoadingStatus.Success)
        }
    }

    @Test
    fun `getCatBreeds on failure catBreeds value is null`() {
        runBlocking {
            Mockito.`when`(repository.getBreeds()).thenReturn(Result.Error(
                "Error code",
                "Error Message"
            ))
            viewModel.getCatBreeds()
            Assert.assertEquals(LiveDataTestUtil.getValue(viewModel.loadingStatus), LoadingStatus.Loading)
            Assert.assertEquals(LiveDataTestUtil.getValue(viewModel.catBreeds), null)
            Assert.assertEquals(LiveDataTestUtil.getValue(viewModel.loadingStatus), LoadingStatus.Error(
                "Error code",
                "Error Message"
            ))
        }
    }


    @Test
    fun `openCatBreedDetails on success navigateToDetails is set`(){
        val catBreed = CatBreed(
            "Cat breed description",
            "id1",
            Image("Image url"),
            "Cat Breed name",
            "Cat Breed Origin",
            "temperament1, temperament2",
            "wikipedia url"
        )
        viewModel.openCatBreedDetails(catBreed)
        Assert.assertEquals(LiveDataTestUtil.getValue(viewModel.navigateToDetails).peekContent(), Event(catBreed).peekContent())
    }

}