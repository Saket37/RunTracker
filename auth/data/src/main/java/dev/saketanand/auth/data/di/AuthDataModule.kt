package dev.saketanand.auth.data.di

import dev.saketanand.auth.data.EmailPatternValidator
import dev.saketanand.auth.domain.PatternValidator
import dev.saketanand.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}