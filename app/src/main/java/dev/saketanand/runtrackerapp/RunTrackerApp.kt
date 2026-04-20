package dev.saketanand.runtrackerapp

import android.app.Application
import dev.saketanand.auth.data.di.authDataModule
import dev.saketanand.auth.presentation.di.authViewModelModule
import dev.saketanand.core.data.di.coreDataModule
import dev.saketanand.run.presentation.di.runViewModelModule
import dev.saketanand.runtrackerapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunTrackerApp)
            modules(
                authDataModule, authViewModelModule, appModule, coreDataModule, runViewModelModule
            )
        }
    }
}