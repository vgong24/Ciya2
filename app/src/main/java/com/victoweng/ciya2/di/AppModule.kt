package com.victoweng.ciya2.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    @JvmStatic fun providesContext(application: Application) : Context {
        return application.applicationContext
    }

}