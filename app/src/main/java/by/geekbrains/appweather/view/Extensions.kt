package by.geekbrains.appweather.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    text: String,
    length: Int = Snackbar.LENGTH_INDEFINITE,
) {
    Snackbar.make(this, text, length).show()
}