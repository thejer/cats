package io.budge.cats.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.LayoutRes
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import io.budge.cats.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showSnackbar(
    snackbarText: String,
    timeLength: Int,
    actionString: String?,
    action: () -> Unit?
) {
    Snackbar.make(this, snackbarText, timeLength).run {
        setAction(actionString) { action() }
        show()
    }
}

inline fun <reified T> ViewGroup.inflate(@LayoutRes layoutRes: Int): T {
    return LayoutInflater.from(context).inflate(layoutRes, this, false) as T
}

fun Activity.viewUrl(url: String) {
    val params = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(resolveColor(R.attr.colorPrimary))
        .build()

    val customTabsIntent = CustomTabsIntent.Builder()
        .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, params)
        .build()
    try {
        customTabsIntent.launchUrl(this, url.toUri())
    } catch (_: ActivityNotFoundException) {
        val chooser = Intent.createChooser(
            Intent(Intent.ACTION_VIEW)
                .setData(url.toUri()), getString(R.string.view_url)
        )
        startActivity(chooser)
    }
}

private fun Activity.resolveColor(@AttrRes attr: Int): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    try {
        return a.getColor(0, 0)
    } finally {
        a.recycle()
    }
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

