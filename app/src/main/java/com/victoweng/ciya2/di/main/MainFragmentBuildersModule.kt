package com.victoweng.ciya2.di.main

import com.victoweng.ciya2.di.main.search.ExampleModule
import com.victoweng.ciya2.ui.SearchHomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector(modules = [ExampleModule::class])
    abstract fun contributeSearchHomeFragment() : SearchHomeFragment
}