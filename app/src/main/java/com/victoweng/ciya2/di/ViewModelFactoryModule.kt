package com.victoweng.ciya2.di

import androidx.lifecycle.ViewModelProvider
import com.victoweng.ciya2.ui.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory) : ViewModelProvider.Factory

}