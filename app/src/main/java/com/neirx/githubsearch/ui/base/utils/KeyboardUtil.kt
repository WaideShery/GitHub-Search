package com.neirx.githubsearch.ui.base.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText





/**
 * Created by Waide Shery on 12.08.19.
 */
fun showKeyboard(editText: EditText) {
    editText.requestFocus()
    editText.setSelection(editText.text.length)
    val imm = editText.context
        .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, 0)
}

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}

