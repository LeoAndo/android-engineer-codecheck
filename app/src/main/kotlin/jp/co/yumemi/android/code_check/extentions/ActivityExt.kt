package jp.co.yumemi.android.code_check.extentions

import android.app.Activity
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val im = getSystemService(InputMethodManager::class.java)
        im.hideSoftInputFromWindow(it.windowToken, 0)
    }
}