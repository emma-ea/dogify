package com.emma_ea.dogify.model

class GetBreedsUsecase {
    suspend fun invoke(): List<Breed> = listOf(Breed("Test get", ""))
}