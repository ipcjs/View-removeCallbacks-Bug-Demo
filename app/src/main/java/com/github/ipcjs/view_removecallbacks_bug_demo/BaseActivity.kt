package com.github.ipcjs.view_removecallbacks_bug_demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView.apply {
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View) {
                    L.d { "activity.detached: $mAttachInfo" }
                }

                override fun onViewAttachedToWindow(v: View) {
                    L.d { "activity.attached: $mAttachInfo" }
                }
            })
            viewTreeObserver.addOnPreDrawListener {
                L.d { "activity.preDraw: $mAttachInfo" }
                true
            }
        }
    }
}

