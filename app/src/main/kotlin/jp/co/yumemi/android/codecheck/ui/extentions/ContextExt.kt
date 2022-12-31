package jp.co.yumemi.android.codecheck.ui.extentions

import android.content.Context
import android.widget.Toast
import jp.co.yumemi.android.codecheck.R

fun Context.showToast(message: String?) {
    val msg = message ?: getString(R.string.default_error_msg)
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}