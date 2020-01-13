package com.victoweng.ciya2.di.main.search

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object ExampleModule {

    @Provides
    @Named("base")
    @JvmStatic fun providesString(): String {
        return "Base String"
    }

    //Inject Named field example
//    @Inject
//    @field:Named("base")
//    lateinit var string: String
}