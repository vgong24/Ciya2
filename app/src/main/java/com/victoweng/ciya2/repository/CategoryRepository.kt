package com.victoweng.ciya2.repository

import androidx.lifecycle.LiveData
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.CategoryType
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object CategoryRepository {

    lateinit var job: CompletableJob

    fun getCategories() : LiveData<List<CategoryType>> {
        job = Job()
        return object : LiveData<List<CategoryType>>() {
            override fun onActive() {
                super.onActive()
                job?.let {
                    CoroutineScope(IO + job).launch {
                        delay(1000)
                        withContext(Main) {
                            value = createCategories()
                            job.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }

    fun createCategories(): List<CategoryType> {
        val categoryList: ArrayList<CategoryType> = ArrayList()
        categoryList.add(CategoryType("ALL", "", R.color.primary_dark_material_dark))
        categoryList.apply {
            add(CategoryType("Sports", "", R.color.colorAccent))
            add(CategoryType("Social", "", R.color.green))
            add(CategoryType("Study", "", R.color.colorPrimaryDark))
            add(CategoryType("Food", "", R.color.error_color_material_dark))
        }

        return categoryList
    }
}