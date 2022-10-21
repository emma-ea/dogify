package com.emma_ea.dogify.model

class FetchBreedUsecase {
    suspend fun invoke(): List<Breed> = listOf(Breed("Test Fetch", ""))
}