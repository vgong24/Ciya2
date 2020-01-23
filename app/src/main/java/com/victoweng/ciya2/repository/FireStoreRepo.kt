package com.victoweng.ciya2.repository

import android.location.Location
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.victoweng.ciya2.comparator.TimeStampComparator
import com.victoweng.ciya2.constants.*
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.data.chat.ChatRoom
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener

object FireStoreRepo {

    private val TAG = FireStoreRepo::class.java.canonicalName

    val fireStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
}