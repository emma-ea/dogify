package com.emma_ea.dogify.model

import com.emma_ea.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ToggleFavouriteStateUsecase : KoinComponent {
    // suspend operator fun invoke(breed: Breed) {}
    private val breedsRepository: BreedsRepository by inject()

    suspend operator fun invoke(breed: Breed) {
        breedsRepository.update(breed.copy(isFavourite = !breed.isFavourite))
    }
}