package dev.saketanand.core.data.di

import dev.saketanand.core.data.auth.EncryptedSessionStorage
import dev.saketanand.core.data.networking.HttpClientFactory
import dev.saketanand.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}