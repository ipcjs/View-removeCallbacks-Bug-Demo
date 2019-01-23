# View-removeCallbacks-Bug-Demo

复现`View.removeCallbacks`移除回调失败的bug

## 复现场景

view未关联到window时, 使用`view.postDelay(runnable)`发送runnable, 关联到window之后, 使用`view.removeCallbacks(runnable)`移除runanble, 则有可能复现该bug

## 产生原因(推测)

(以下分析以Api21的源码为例)

1. 当View没有关联window时, `View.postDelay(runnable)`发送的runnable会放到[ViewRootImpl.getRunQueue()](https://github.com/aosp-mirror/platform_frameworks_base/blob/lollipop-release/core/java/android/view/ViewRootImpl.java#L6518)中
2. window每次执行`performTraversals()`时, 都会将`runQueue`中的runnable取出来放到自身的`mHandler`中执行([详见源码](https://github.com/aosp-mirror/platform_frameworks_base/blob/lollipop-release/core/java/android/view/ViewRootImpl.java#L1323))
3. `runQueue`是线程本地的, window是主线程中的, 但一个主线程可能有多个window(相同进程中的多个Activity)
4. 已示例为例, `MainActivity`打开`NextActivity`时, 若在NextActivity关联到window之前MainActivity执行了一次`performTraversals()`, 则NextActivity中post的runnable会跑到MainActivity的mHandler中执行, 导致在NextActivity中remove失败

## 规避方法

如示例所示, 使用[CancelableRunnable](app/src/main/java/com/github/ipcjs/view_removecallbacks_bug_demo/CancelableRunnable.kt)替代Runnable

## 出现bug的版本

Api24+已经修复了, 相关提交应该是这个: <https://github.com/aosp-mirror/platform_frameworks_base/commit/bea0c7daa6611d8b96e1271f8854f500a87342fc>

