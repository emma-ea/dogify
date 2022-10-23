package com.emma_ea.dogify.model

import com.emma_ea.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetBreedsUsecase : KoinComponent {
    // suspend fun invoke(): List<Breed> = listOf(Breed("Test get", ""))
    private val breedsRepository: BreedsRepository by inject()
    suspend fun invoke(): List<Breed> =
        breedsRepository.get()
}