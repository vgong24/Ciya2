package com.victoweng.ciya2.di

import com.victoweng.ciya2.services.MessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMessagingService(): MessagingService
}