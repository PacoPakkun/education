package com.movietracker

import android.util.Log

fun Any.logd(message: Any? = "") {
    Log.d(this.javaClass.simpleName, message.toString())
}