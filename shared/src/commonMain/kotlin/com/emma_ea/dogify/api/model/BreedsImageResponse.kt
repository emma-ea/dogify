package com.emma_ea.dogify.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BreedsImageResponse(@SerialName("message") val breedImageUrl: String)