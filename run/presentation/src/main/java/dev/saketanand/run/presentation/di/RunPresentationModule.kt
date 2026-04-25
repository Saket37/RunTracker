package dev.saketanand.run.presentation.di

import dev.saketanand.run.domian.RunningTracker
import dev.saketanand.run.presentation.active_run.ActiveRunViewModel
import dev.saketanand.run.presentation.run_overview.RunOverviewViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}