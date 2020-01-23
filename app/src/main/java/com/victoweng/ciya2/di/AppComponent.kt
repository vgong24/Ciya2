package com.victoweng.ciya2.di

import android.app.Application
import com.victoweng.ciya2.BaseApplication
import com.victoweng.ciya2.di.repository.database.DatabaseModule
import com.victoweng.ciya2.di.repository.firebase.FireBaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

//https://codingwithmitch.com/courses/dagger22-android/subcomponents-dagger2-android/
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        ServiceBuildersModule::class,
        ViewModelFactoryModule::class,
        AppModule::class,
        DatabaseModule::class,
        FireBaseModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}