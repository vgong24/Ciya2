package com.victoweng.ciya2.ui.eventcreation

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.victoweng.ciya2.data.CategoryType
import com.victoweng.ciya2.repository.CategoryRepository

class EventCreationViewModel : ViewModel() {

    val categoryLiveData: LiveData<List<CategoryType>> = CategoryRepository.getCategories()
}