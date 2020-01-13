package com.victoweng.ciya2.util

import android.content.Context
import android.widget.Toast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToastUtil @Inject constructor(private val context: Context){
    fun show(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}