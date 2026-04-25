package dev.saketanand.run.location.di

import dev.saketanand.run.domian.LocationObserver
import dev.saketanand.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}