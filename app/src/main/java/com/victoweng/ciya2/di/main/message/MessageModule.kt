package com.victoweng.ciya2.di.main.message

import com.victoweng.ciya2.di.scopes.MessageScope
import com.victoweng.ciya2.repository.database.AppDatabase
import com.victoweng.ciya2.repository.database.messages.MessageDao
import dagger.Module
import dagger.Provides

@Module
object MessageModule {

    @Provides
    @MessageScope
    @JvmStatic
    fun providesMessageDao(appDatabase: AppDatabase): MessageDao {
        return appDatabase.messageDao()
    }

}