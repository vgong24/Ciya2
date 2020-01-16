package com.victoweng.ciya2.di.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.victoweng.ciya2.repository.auth.AuthRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AuthModule {

    @Provides
    @JvmStatic
    fun providesAuthRepo(firebaseAuth: FirebaseAuth, firebaseDatabase: FirebaseDatabase, firestore: FirebaseFirestore): AuthRepo {
        return AuthRepo(firebaseAuth, firebaseDatabase, firestore)
    }
}