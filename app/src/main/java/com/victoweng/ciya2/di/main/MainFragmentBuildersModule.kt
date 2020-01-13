package com.victoweng.ciya2.di.main

import android.os.Bundle
import com.victoweng.ciya2.di.main.search.ExampleModule
import com.victoweng.ciya2.di.main.search.SearchScope
import com.victoweng.ciya2.ui.FriendsListFragment
import com.victoweng.ciya2.ui.auth.CreateUsernameFragment
import com.victoweng.ciya2.ui.auth.LoginFragment
import com.victoweng.ciya2.ui.dashboard.DashboardFragment
import com.victoweng.ciya2.ui.eventcreation.ChooseLocationFragment
import com.victoweng.ciya2.ui.eventcreation.EnterDetailsFragment
import com.victoweng.ciya2.ui.eventcreation.EventCreationFragment
import com.victoweng.ciya2.ui.message.MessageFragment
import com.victoweng.ciya2.ui.message.MessagesListFragment
import com.victoweng.ciya2.ui.searchEvent.FullEventDetailsFragment
import com.victoweng.ciya2.ui.searchEvent.SearchHomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    //Authentication
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment() : LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateUsernameFragment() : CreateUsernameFragment

    //View and Modify Events
    @ContributesAndroidInjector(modules = [ExampleModule::class])
    abstract fun contributeSearchHomeFragment() : SearchHomeFragment

    @ContributesAndroidInjector
    abstract fun contibuteEventCreationFragment() : EventCreationFragment

    @ContributesAndroidInjector
    abstract fun contributeChooseLocationFragment() : ChooseLocationFragment

    @ContributesAndroidInjector
    abstract fun contributeEnterDetailsFragment() : EnterDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeFullEventDetailsFragment() : FullEventDetailsFragment

    //Messaging
    @ContributesAndroidInjector
    abstract fun contributeMessageListFragment() : MessagesListFragment

    @ContributesAndroidInjector
    abstract fun contributeMessageFragment() : MessageFragment

    //Friends and others
    @ContributesAndroidInjector
    abstract fun contributeFriendsListFragment() : FriendsListFragment

    @ContributesAndroidInjector(modules = [])
    abstract fun contributeDashboardFragment() : DashboardFragment

}