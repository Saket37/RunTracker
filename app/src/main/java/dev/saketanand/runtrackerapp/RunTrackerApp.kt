package dev.saketanand.runtrackerapp

import android.app.Application
import dev.saketanand.auth.data.di.authDataModule
import dev.saketanand.auth.presentation.di.authViewModelModule
import dev.saketanand.core.data.di.coreDataModule
import dev.saketanand.core.database.di.databaseModule
import dev.saketanand.run.location.di.locationModule
import dev.saketanand.run.presentation.di.runPresentationModule
import dev.saketanand.runtrackerapp.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunTrackerApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunTrackerApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule
            )
        }
    }
}