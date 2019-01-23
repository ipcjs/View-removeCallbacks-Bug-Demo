package com.github.ipcjs.view_removecallbacks_bug_demo

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

private val attachInfoField = View::class.java.getDeclaredField("mAttachInfo").apply { isAccessible = true }
val View.mAttachInfo
    get() = try {
        attachInfoField.get(this)
    } catch (e: Exception) {
        null
    }

val Activity.rootView: View get() = findViewById<View>(android.R.id.content).rootView

fun Context.toast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

val Activity.context: Context get() = this

fun ViewGroup.text(init: TextView.() -> Unit) = initView(TextView(context), init)

fun ViewGroup.button(init: Button.() -> Unit) = initView(Button(context), init)

fun ViewGroup.progress(init: ProgressBar.() -> Unit) = initView(ProgressBar(context), init)

inline fun <reified V : View> ViewGroup.initView(view: V, init: V.() -> Unit): V {
    view.init()
    addView(view)
    return view
}