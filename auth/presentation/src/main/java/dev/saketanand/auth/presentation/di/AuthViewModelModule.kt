package dev.saketanand.auth.presentation.di

import dev.saketanand.auth.presentation.login.LoginViewModel
import dev.saketanand.auth.presentation.register.RegisterVieModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterVieModel)
    viewModelOf(::LoginViewModel)
}