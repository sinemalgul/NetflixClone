package com.example.netflixclone.common

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(text: String) {
    Snackbar.make(this, text, 1000).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun setViewsVisible(vararg views: View) {
    views.forEach { it.visible() }
}

fun setViewsGone(vararg views: View) {
    views.forEach { it.gone() }
}
