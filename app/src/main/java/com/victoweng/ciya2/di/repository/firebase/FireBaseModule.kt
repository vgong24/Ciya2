package com.victoweng.ciya2.di.repository.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FireBaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}