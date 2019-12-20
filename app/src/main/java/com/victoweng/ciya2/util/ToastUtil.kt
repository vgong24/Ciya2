package com.victoweng.ciya2.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun show(context:Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}