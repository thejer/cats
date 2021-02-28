package io.budge.cats.ui.catbreeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.budge.cats.data.api.ICatsRepository
import io.budge.cats.data.model.CatBreed
import io.budge.cats.data.utils.LoadingStatus
import io.budge.cats.data.utils.Result
import io.budge.cats.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatBreedsViewModel @Inject constructor(
    private val catRepository: ICatsRepository
): ViewModel() {
    
    private val _catBreeds = MutableLiveData<MutableList<CatBreed>>()
    val catBreeds: LiveData<MutableList<CatBreed>>
        get() = _catBreeds

    private val _loadingStatus = MutableLiveData<LoadingStatus>()

    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    private val _navigateToDetails = MutableLiveData<Event<CatBreed>>()
    val navigateToDetails : LiveData<Event<CatBreed>>
        get() = _navigateToDetails

    init {
        getCatBreeds()
    }


    fun openCatBreedDetails(catBreed: CatBreed){
        _navigateToDetails.value = Event(catBreed)
    }

    fun getCatBreeds() {
        _loadingStatus.value = LoadingStatus.Loading
        viewModelScope.launch {
            when (val result = catRepository.getBreeds()) {
                is Result.Success -> {
                    _loadingStatus.value = LoadingStatus.Success
                    _catBreeds.value = result.data.also { breeds ->
                        breeds.removeIf { it.image == null || it.wikipediaUrl == null }
                    }
                }
                is Result.Error -> {
                    _loadingStatus.value = LoadingStatus.Error(result.errorCode, result.errorMessage)
                }
            }
        }
    }
}