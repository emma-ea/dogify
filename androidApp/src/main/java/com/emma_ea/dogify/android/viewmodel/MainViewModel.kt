package com.emma_ea.dogify.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emma_ea.dogify.model.Breed
import com.emma_ea.dogify.usecase.FetchBreedUsecase
import com.emma_ea.dogify.usecase.GetBreedsUsecase
import com.emma_ea.dogify.usecase.ToggleFavouriteStateUsecase
import com.emma_ea.dogify.repository.BreedsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    breedsRepository: BreedsRepository,
    private val getBreeds: GetBreedsUsecase,
    private val fetchBreeds: FetchBreedUsecase,
    private val onToggleFavouriteState: ToggleFavouriteStateUsecase
) : ViewModel() {

    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events

    private val _shouldFilterFavourites = MutableStateFlow(false)
    val shouldFilterFavourites: StateFlow<Boolean> = _shouldFilterFavourites

    private val _favouritesCount = MutableStateFlow(0)
    val favouritesCount: StateFlow<Int> = _favouritesCount

    val breeds = breedsRepository.breeds.combine(shouldFilterFavourites) {
            breeds, shouldFilterFavourites ->
            if (shouldFilterFavourites) {
                breeds.filter { it.isFavourite }
            }else {
                breeds
            }.also {
                _state.value = if (it.isEmpty()) State.EMPTY else State.NORMAL
                _favouritesCount.value = breeds.count { it.isFavourite }
            }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    init {
        loadData()
    }

    fun refresh() {
        loadData(true)
    }

    private fun loadData(isForceRefresh: Boolean = false) {
        val getData: suspend () -> List<Breed> =
            {
                if (isForceRefresh) fetchBreeds.invoke()
                else getBreeds.invoke()
            }

        if (isForceRefresh) {
            _isRefreshing.value = true
        } else {
            _state.value = State.LOADING
        }

        viewModelScope.launch {
            _state.value = try {
                getData()
                State.NORMAL
            } catch (e: Exception) {
                State.ERROR
            }
            _isRefreshing.value = false
        }
    }

    fun onToggleFavouriteFilter() {
        _shouldFilterFavourites.value = !_shouldFilterFavourites.value
    }

    fun onFavouriteTapped(breed: Breed) {
        viewModelScope.launch {
            try {
                onToggleFavouriteState(breed)
            } catch (e: Exception) {
                _events.emit(Event.ERROR)
            }
        }
    }

    enum class State {
        LOADING, NORMAL, ERROR, EMPTY
    }

    enum class Event {
        ERROR
    }

}

