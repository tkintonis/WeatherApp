package com.example.weatherapp.common

import android.content.res.Resources
import android.graphics.Color.red
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.Resource
import com.google.android.material.snackbar.Snackbar
import com.mcxiaoke.koi.ext.getInputMethodManager

fun Fragment.showKeyboard(view: View) {
    view.requestFocus()
    requireActivity()
        .getInputMethodManager()
        .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.hideKeyboard(view: View) {
    requireActivity()
        .getInputMethodManager()
        .hideSoftInputFromWindow(view.windowToken, 0)
}

fun showErrorMessage(view: View, message: String, resources: Resources, action: () -> Unit) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.red, null))
        .setActionTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        .apply {
            dismiss()
            setAction(R.string.retry) { action.invoke() }
        }
        .show()
}

fun showErrorMessage(view: View, message: String, resources: Resources) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.red, null))
        .setActionTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        .apply {
            dismiss()
        }
        .show()
}