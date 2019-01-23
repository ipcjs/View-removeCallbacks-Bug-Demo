package com.github.ipcjs.view_removecallbacks_bug_demo

import android.os.Bundle

class NextActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView.apply {
            val runnable = CancelableRunnable {
                if (isCancelled()) {
                    toast("复现成功!")
                }
            }
            postDelayed(runnable, 500)

            postDelayed({
                removeCallbacks(runnable)
                runnable.cancel()
            }, 100)
        }

    }

}
