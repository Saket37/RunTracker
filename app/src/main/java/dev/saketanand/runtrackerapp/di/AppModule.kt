package dev.saketanand.runtrackerapp.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dev.saketanand.runtrackerapp.MainViewModel
import dev.saketanand.runtrackerapp.RunTrackerApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {

        EncryptedSharedPreferences(
            context = androidApplication(), fileName = "auth_pref", MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<CoroutineScope> {
        (androidApplication() as RunTrackerApp).applicationScope
    }

    viewModelOf(::MainViewModel)
}

