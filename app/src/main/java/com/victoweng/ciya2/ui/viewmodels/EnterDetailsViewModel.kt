package com.victoweng.ciya2.ui.viewmodels

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.victoweng.ciya2.R
import com.victoweng.ciya2.constants.EVENT_CATEGORY_TYPE
import com.victoweng.ciya2.constants.EVENT_LOCATION
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.*
import com.victoweng.ciya2.repository.FireStoreRepo
import com.victoweng.ciya2.util.ToastUtil
import com.victoweng.ciya2.util.date.DateBuilder
import com.victoweng.ciya2.util.date.DateTimeUtil
import java.time.LocalDateTime

class EnterDetailsViewModel : ViewModel(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    val titleLiveData = MutableLiveData<String>("")
    val descriptionLiveData = MutableLiveData<String>("")
    val eventDateLiveData = MutableLiveData<DateBuilder>()

    lateinit var location : EventLocation
    lateinit var category: CategoryType

    //Setup user input
    fun setTitle (title: String) {
        titleLiveData.value = title
    }

    fun setDescription(description: String) {
        descriptionLiveData.value = description
    }

    fun getDateLiveData() : LiveData<DateBuilder> {
        if (eventDateLiveData.value == null) {
            eventDateLiveData.value = DateBuilder()
        }
        return eventDateLiveData
    }

    fun showDatePicker(context: Context) {
        val date = getDateLiveData().value!!
        Log.d("CLOWN", "showDatePicker year: ${date.getYear()}")
        val datePickerDialog = DatePickerDialog(context, this, date.getYear(), date.getMonth(), date.getDay())
        datePickerDialog.datePicker.minDate = DateTimeUtil.getMinDate()
        datePickerDialog.show()
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dateOfMonth: Int) {
        setDate(dateOfMonth, month, year)
        showTimePicker(datePicker?.context!!)
    }

    fun showTimePicker(context: Context) {
        val date = getDateLiveData().value!!.build()
        val timePickerDialog = TimePickerDialog(context, this, date.hours, date.minutes, false)
        timePickerDialog.show()
    }

    override fun onTimeSet(timePicker: TimePicker?, hour: Int, minute: Int) {
        if (timeIsAfterNow(hour, minute)) {
            setTime(hour, minute)
        } else {
            ToastUtil.show(timePicker?.context!!, "Time needs to be after current time to be valid")
            showTimePicker(timePicker?.context!!)
        }
    }

    private fun timeIsAfterNow(hour: Int, minute: Int): Boolean {
        val dateData = getDateLiveData().value
        dateData?.let {
            it.hour(hour)
            it.minute(minute)
        }
        return dateData?.build()!!.after(DateTimeUtil.getCurrentTime())
    }

    fun setDate(day: Int, month: Int, year: Int) {
        val dateBuilder = getDateLiveData().value
        dateBuilder?.let {
            it.day(day)
            it.month(month)
            it.year(year)
        }
        eventDateLiveData.value = dateBuilder
    }

    fun setTime(hour: Int, minute: Int) {
        val dateBuilder = getDateLiveData().value
        dateBuilder?.let {
            it.hour(hour)
            it.minute(minute)
        }
        eventDateLiveData.value = dateBuilder
    }

    fun setArguments(arguments: Bundle) {
        location = arguments.get(EVENT_LOCATION) as EventLocation
        category = arguments.get(EVENT_CATEGORY_TYPE) as CategoryType
    }

    fun getEventDetail() : EventDetail {
        val user = UserProfile(FireRepo.getCurrentUserId()!!, FireRepo.getCurrentUser()!!.email!!, FireRepo.getCurrentUser()!!.displayName!!)
        val date = Timestamp(getDateLiveData().value!!.build())
        val userList = UserProfiles()
        userList.addUser(user)
        return EventDetail(user, category, location, titleLiveData.value!!, descriptionLiveData.value!!, date, participants = userList)
    }

    fun createEvent(navController: NavController) {
        if(!allFieldsFilled()) {
            return
        }
        val task = FireStoreRepo.createEvent(getEventDetail())
        task?.addOnCompleteListener {
            Log.d("debug", "change to search home")
            navController.navigate(R.id.searchHomeFragment)
        }

    }

    private fun allFieldsFilled(): Boolean {
        return !titleLiveData.value!!.isEmpty() && !descriptionLiveData.value!!.isEmpty()
    }

}