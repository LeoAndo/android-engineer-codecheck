package jp.co.yumemi.android.code_check.ui.extentions

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    view?.windowToken.let {
        val im =
            requireActivity().getSystemService(InputMethodManager::class.java)
        im.hideSoftInputFromWindow(it, 0)
    }
}