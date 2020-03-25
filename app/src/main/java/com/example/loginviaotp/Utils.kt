package com.example.loginviaotp

import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Int.isNumeric(): Boolean {
    if (this == KeyEvent.KEYCODE_0
        || this == KeyEvent.KEYCODE_1
        || this == KeyEvent.KEYCODE_2
        || this == KeyEvent.KEYCODE_3
        || this == KeyEvent.KEYCODE_4
        || this == KeyEvent.KEYCODE_5
        || this == KeyEvent.KEYCODE_6
        || this == KeyEvent.KEYCODE_7
        || this == KeyEvent.KEYCODE_8
        || this == KeyEvent.KEYCODE_9
    ) {
        return true
    }
    return false
}

object Utils {
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}