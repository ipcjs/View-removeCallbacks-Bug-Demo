package com.github.ipcjs.view_removecallbacks_bug_demo

import android.util.Log

object L {
    inline fun d(block: () -> String) {
        Log.d("setShowWithDelay", block())
    }
    inline fun w(block: () -> String) {
        Log.w("setShowWithDelay", block())
    }

}