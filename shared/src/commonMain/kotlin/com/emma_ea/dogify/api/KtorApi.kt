package com.emma_ea.dogify.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal abstract class KtorApi {

    private val jsonConfiguration = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    val apiClient = HttpClient {
        install(ContentNegotiation) {
            json(jsonConfiguration)
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(Url("https://dog.ceo"))
            path("api", path)
        }
    }

}