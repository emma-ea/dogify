package com.emma_ea.dogify.di

import com.emma_ea.dogify.model.FetchBreedUsecase
import com.emma_ea.dogify.model.GetBreedsUsecase
import com.emma_ea.dogify.model.ToggleFavouriteStateUsecase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val useCaseModule = module {
    factory { GetBreedsUsecase() }
    factory { FetchBreedUsecase() }
    factory {ToggleFavouriteStateUsecase() }
}

private val sharedModules = listOf(useCaseModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedModules)
    }
