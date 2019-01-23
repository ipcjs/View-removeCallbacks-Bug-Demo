package com.github.ipcjs.view_removecallbacks_bug_demo

import android.app.Activity
import android.content.Context
import android.view.View
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