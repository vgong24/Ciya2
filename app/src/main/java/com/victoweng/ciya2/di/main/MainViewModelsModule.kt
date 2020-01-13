package com.victoweng.ciya2.di.main

import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.di.ViewModelKey
import com.victoweng.ciya2.ui.auth.CreateUserNameViewModel
import com.victoweng.ciya2.ui.auth.LoginViewModel
import com.victoweng.ciya2.ui.eventcreation.ChooseLocationViewModel
import com.victoweng.ciya2.ui.eventcreation.EnterDetailsViewModel
import com.victoweng.ciya2.ui.eventcreation.EventCreationViewModel
import com.victoweng.ciya2.ui.message.MessageListViewModel
import com.victoweng.ciya2.ui.searchEvent.SearchHomeViewModel
import com.victoweng.ciya2.ui.viewmodels.FriendsListViewModel
import com.victoweng.ciya2.ui.viewmodels.FullEventDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {

    //Auth
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(loginViewModel: LoginViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateUserNameViewModel::class)
    abstract fun bindsCreateUserNameViewModel(viewModel: CreateUserNameViewModel): ViewModel

    //Event based view models
    @Binds
    @IntoMap
    @ViewModelKey(SearchHomeViewModel::class)
    abstract fun bindsSearchHomeViewModel(searchHomeViewModel: SearchHomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventCreationViewModel::class)
    abstract fun bindsEventCreationViewModel(eventCreationViewModel: EventCreationViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseLocationViewModel::class)
    abstract fun bindsChooseLocationViewModel(chooseLocationViewModel: ChooseLocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EnterDetailsViewModel::class)
    abstract fun bindsEnterDetailsViewModel(enterDetailsViewModel: EnterDetailsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FullEventDetailsViewModel::class)
    abstract fun bindsFullEventDetailsViewModel(fullEventDetailsViewModel: FullEventDetailsViewModel): ViewModel

    //Messaging
    @Binds
    @IntoMap
    @ViewModelKey(MessageListViewModel::class)
    abstract fun bindsMessageListViewModel(messageListViewModel: MessageListViewModel): ViewModel


    //Friends
    @Binds
    @IntoMap
    @ViewModelKey(FriendsListViewModel::class)
    abstract fun bindsFriendsListViewModel(friendsListViewModel: FriendsListViewModel): ViewModel

    //Dashboard
}