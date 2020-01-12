package com.victoweng.ciya2.di

import com.victoweng.ciya2.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun activity(): MainActivity
}