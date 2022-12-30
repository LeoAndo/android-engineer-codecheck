package jp.co.yumemi.android.code_check.ui.extentions

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import jp.co.yumemi.android.code_check.R

fun Fragment.hideKeyboard() {
    view?.windowToken.let {
        val im =
            requireActivity().getSystemService(InputMethodManager::class.java)
        im.hideSoftInputFromWindow(it, 0)
    }
}

fun Fragment.showAlertDialog(
    title: String = requireContext().getString(R.string.app_name),
    message: String? = null,
    positiveButtonText: String? = requireContext().getString(android.R.string.ok),
    negativeButtonText: String? = requireContext().getString(android.R.string.cancel),
    onClickPositiveButton: (() -> Unit)? = null,
    onClickNegativeButton: (() -> Unit)? = null,
) {
    MaterialAlertDialogBuilder(requireContext()).apply {
        setTitle(title)
        message?.let { setMessage(it) }
        positiveButtonText?.let { setPositiveButton(it) { _, _ -> onClickPositiveButton?.invoke() } }
        negativeButtonText?.let { setNegativeButton(it) { _, _ -> onClickNegativeButton?.invoke() } }
    }.create().show()
}