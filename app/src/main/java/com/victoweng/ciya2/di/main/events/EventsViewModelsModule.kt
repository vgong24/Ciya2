package com.victoweng.ciya2.di.main.events

import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.di.ViewModelKey
import com.victoweng.ciya2.ui.eventcreation.EnterDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EventsViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EnterDetailsViewModel::class)
    abstract fun bindsEnterDetailsViewModel(enterDetailsViewModel: EnterDetailsViewModel): ViewModel

}