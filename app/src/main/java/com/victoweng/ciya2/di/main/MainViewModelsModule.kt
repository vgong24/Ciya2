package com.victoweng.ciya2.di.main

import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.di.ViewModelKey
import com.victoweng.ciya2.ui.searchEvent.SearchHomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchHomeViewModel::class)
    abstract fun bindsSearchHomeViewModel(searchHomeViewModel: SearchHomeViewModel) : ViewModel

}