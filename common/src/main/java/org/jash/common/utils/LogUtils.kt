package org.jash.common.utils

import android.util.Log

val Any.TAG
    get() = javaClass.simpleName

fun Any.logging(msg: Any?) {
    Log.d(TAG, msg.toString())
}