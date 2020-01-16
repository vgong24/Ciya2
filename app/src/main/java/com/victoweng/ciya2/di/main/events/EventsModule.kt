package com.victoweng.ciya2.di.main.events

import com.victoweng.ciya2.repository.database.AppDatabase
import com.victoweng.ciya2.repository.database.event.EventsDao
import dagger.Module
import dagger.Provides

@Module
object EventsModule {

    @Provides
    @JvmStatic
    fun providesEventsDao(appDatabase: AppDatabase): EventsDao {
        return appDatabase.eventsDao()
    }
}