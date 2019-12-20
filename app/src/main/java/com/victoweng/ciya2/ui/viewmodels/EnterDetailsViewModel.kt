package com.victoweng.ciya2.ui.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.victoweng.ciya2.R
import com.victoweng.ciya2.constants.EVENT_CATEGORY_TYPE
import com.victoweng.ciya2.constants.EVENT_LOCATION
import com.victoweng.ciya2.constants.FIRE_EVENT_DETAILS
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.CategoryType
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.EventLocation
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.repository.FireStoreRepo

class EnterDetailsViewModel : ViewModel() {
    val database = FirebaseDatabase.getInstance()
    val eventRef = database.getReference(FIRE_EVENT_DETAILS)
    val collectionDb = FirebaseFirestore.getInstance().collection("eventDetail")

    val titleLiveData = MutableLiveData<String>("")
    val descriptionLiveData = MutableLiveData<String>("")

    lateinit var location : EventLocation
    lateinit var category: CategoryType

    fun setTitle (title: String) {
        titleLiveData.value = title
    }

    fun setDescription(description: String) {
        descriptionLiveData.value = description
    }

    fun setArguments(arguments: Bundle) {
        location = arguments.get(EVENT_LOCATION) as EventLocation
        category = arguments.get(EVENT_CATEGORY_TYPE) as CategoryType
    }

    fun getEventDetail() : EventDetail {
        val user = UserProfile(FireRepo.getCurrentUserId()!!, FireRepo.getCurrentUser()!!.email!!, "username")
        return EventDetail(user, category, location, titleLiveData.value!!, descriptionLiveData.value!!, "")
    }

    fun createEvent(navController: NavController) {
        val task = FireStoreRepo.createEvent(getEventDetail())
        task?.addOnCompleteListener {
            Log.d("debug", "change to search home")
            navController.navigate(R.id.searchHomeFragment)
        }

    }

}