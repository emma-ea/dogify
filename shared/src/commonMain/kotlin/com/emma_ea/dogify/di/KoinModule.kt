package com.emma_ea.dogify.di

import com.emma_ea.dogify.api.BreedsApi
import com.emma_ea.dogify.database.createDriver
import com.emma_ea.dogify.db.DogifyDatabase
import com.emma_ea.dogify.usecase.FetchBreedUsecase
import com.emma_ea.dogify.usecase.GetBreedsUsecase
import com.emma_ea.dogify.usecase.ToggleFavouriteStateUsecase
import com.emma_ea.dogify.repository.BreedsLocalSource
import com.emma_ea.dogify.repository.BreedsRemoteSource
import com.emma_ea.dogify.repository.BreedsRepository
import com.emma_ea.dogify.utils.getDispatcherProvider
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val useCaseModule = module {
    factory { GetBreedsUsecase() }
    factory { FetchBreedUsecase() }
    factory { ToggleFavouriteStateUsecase() }
}

private val repositories = module {
    single { BreedsRepository(get(), get()) }
    factory { BreedsRemoteSource(get(), get()) }
    factory { BreedsLocalSource(get(), get()) }
}

private val apiModule = module {
    factory { BreedsApi() }
}

private val utilityModule = module {
    factory { getDispatcherProvider() }
}

private val databaseModule = module {
    single { DogifyDatabase(createDriver("dogify.db")) }
}

private val sharedModules = listOf(
    useCaseModule, repositories, apiModule, utilityModule, databaseModule
)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedModules)
    }
