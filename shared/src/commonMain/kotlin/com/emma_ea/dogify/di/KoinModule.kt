package com.emma_ea.dogify.di

import com.emma_ea.dogify.api.BreedsApi
import com.emma_ea.dogify.db.createDriver
import com.emma_ea.dogify.dogify.db.DogifyDatabase
import com.emma_ea.dogify.model.FetchBreedUsecase
import com.emma_ea.dogify.model.GetBreedsUsecase
import com.emma_ea.dogify.model.ToggleFavouriteStateUsecase
import com.emma_ea.dogify.repository.BreedsRemoteSource
import com.emma_ea.dogify.repository.BreedsRepository
import com.emma_ea.dogify.utils.getDispatcherProvider
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val useCaseModule = module {
    factory { GetBreedsUsecase() }
    factory { FetchBreedUsecase() }
    factory {ToggleFavouriteStateUsecase() }
}

private val repositories = module {
    single { BreedsRepository(get()) }
    factory { BreedsRemoteSource(get(), get()) }
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
    useCaseModule, repositories, apiModule, utilityModule
)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedModules)
    }
