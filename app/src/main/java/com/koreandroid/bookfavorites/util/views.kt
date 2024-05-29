package com.koreandroid.bookfavorites.util

import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showSoftKeyboard(): Boolean = if (requestFocus()) {
    val imm = context.getSystemService(InputMethodManager::class.java)

    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
} else false

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(InputMethodManager::class.java)

    imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}