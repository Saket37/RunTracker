package dev.saketanand.core.database.di

import androidx.room.Room.databaseBuilder
import dev.saketanand.core.database.RoomLocalRunDataSource
import dev.saketanand.core.database.RunDatabase
import dev.saketanand.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        databaseBuilder(
            androidApplication(),
            RunDatabase::class.java,
            "run.db"
        ).build()
    }
    single {
        get<RunDatabase>().runDao
    }

    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}