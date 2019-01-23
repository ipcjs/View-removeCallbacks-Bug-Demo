package com.github.ipcjs.view_removecallbacks_bug_demo

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible

class NextActivity : BaseActivity() {
    lateinit var _textView: TextView
    lateinit var _progressBar: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView())
        rootView.apply {
            L.d { "view.postDelayed, $mAttachInfo" }
            val runnable = CancelableRunnable {
                if (isCancelled()) {
                    _textView.text = "复现成功!"
                    L.w { "被取消的runnable依然被执行了..." }
                }
            }
            postDelayed(runnable, 500)

            postDelayed({
                L.d { "view.removeCallbacks, $mAttachInfo" }
                removeCallbacks(runnable)
                runnable.cancel()
            }, 100)
        }

    }

    private fun createContentView(): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            _textView = text {
                text = "未复现"
                gravity = Gravity.CENTER
                textSize = 30f
            }
            button {
                text = "Show/Hide ProgressBar"
                setOnClickListener {
                    _progressBar.isVisible = !_progressBar.isVisible
                }
            }
            _progressBar = progress {
                isVisible = false
            }
        }
    }

}
