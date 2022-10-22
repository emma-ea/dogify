package com.emma_ea.dogify.api

import com.emma_ea.dogify.api.model.BreedsImageResponse
import com.emma_ea.dogify.api.model.BreedsResponse
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class BreedsApi : KtorApi() {

    suspend fun getBreeds(): BreedsResponse =
        apiClient.get {
            apiUrl("breeds/list")
        }.body()

    suspend fun getRandomBreedImageFor(breed: String): BreedsImageResponse =
        apiClient.get {
            apiUrl("breed/$breed/images/random")
        }.body()
}