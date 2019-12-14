package com.victoweng.ciya2.ui.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.victoweng.ciya2.constants.EVENT_CATEGORY_TYPE
import com.victoweng.ciya2.constants.EVENT_LOCATION
import com.victoweng.ciya2.constants.FIRE_EVENT_DETAILS
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.CategoryType
import com.victoweng.ciya2.data.EventDetail
import com.victoweng.ciya2.data.EventLocation
import com.victoweng.ciya2.data.UserProfile

class EnterDetailsViewModel : ViewModel() {
    val database = FirebaseDatabase.getInstance()
    val eventRef = database.getReference(FIRE_EVENT_DETAILS)
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
        val user = UserProfile("fake", "fame")
        return EventDetail(user, category, location, titleLiveData.value!!, descriptionLiveData.value!!, "")
    }

    fun createEvent() {
        eventRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(EventDetail::class.java)
                Log.d("CLOWN", "data added: " + value?.toString())
            }

            override fun onCancelled(snapshot: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        eventRef.setValue(getEventDetail())
        database.reference.child("test").setValue("first try")
        FirebaseAuth.getInstance().signOut()
    }

}