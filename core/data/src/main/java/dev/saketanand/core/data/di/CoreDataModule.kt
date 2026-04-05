package dev.saketanand.core.data.di

import dev.saketanand.core.data.networking.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataModule = module {
    single{
        HttpClientFactory().build()
    }
}