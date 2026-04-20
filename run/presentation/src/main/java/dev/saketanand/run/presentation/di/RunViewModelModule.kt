package dev.saketanand.run.presentation.di

import dev.saketanand.run.presentation.active_run.ActiveRunViewModel
import dev.saketanand.run.presentation.run_overview.RunOverviewViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val runViewModelModule = module {
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}