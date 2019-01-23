package com.github.ipcjs.view_removecallbacks_bug_demo

class CancelableRunnable(private val block: CancelableRunnable.() -> Unit) : Runnable {
    private var _isCancelled = false
    fun isCancelled(): Boolean = _isCancelled
    fun cancel() {
        _isCancelled = true
    }

    override fun run() {
        block()
    }
}