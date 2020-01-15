package com.victoweng.ciya2.di.main.message

import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.di.ViewModelKey
import com.victoweng.ciya2.ui.message.MessageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MessageViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindsMessageViewModel(messageViewModel: MessageViewModel): ViewModel

}