package com.victoweng.ciya2.di.repository.database

import android.content.Context
import com.victoweng.ciya2.repository.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

     @Provides
     @Singleton
     @JvmStatic
     fun providesRoomDatabase(context: Context): AppDatabase {
         return AppDatabase(context)
     }
}