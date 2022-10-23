package com.emma_ea.dogify.usecase

import com.emma_ea.dogify.model.Breed
import com.emma_ea.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchBreedUsecase : KoinComponent {
    // suspend fun invoke(): List<Breed> = listOf(Breed("Test Fetch", ""))
    private val breedsRepository: BreedsRepository by inject()
    suspend fun invoke(): List<Breed> =
        breedsRepository.fetch()
}