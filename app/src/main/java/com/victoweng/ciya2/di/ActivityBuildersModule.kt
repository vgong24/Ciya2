package com.victoweng.ciya2.di

import com.victoweng.ciya2.di.main.MainFragmentBuildersModule
import com.victoweng.ciya2.di.main.MainViewModelsModule
import com.victoweng.ciya2.di.main.MainScope
import com.victoweng.ciya2.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Creates the SubComponents for Activity Level
 */
@Module
abstract class ActivityBuildersModule {

    //MainFragmentBuildersModule creates subcomponents in fragment level
    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class, MainViewModelsModule::class])
    abstract fun contributesMainActivity(): MainActivity
}