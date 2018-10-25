package hhxk.util

import android.app.Activity
import java.util.*

class ActiivtyStack {
    companion object {
        private val mActivityStack = Stack<Activity>()
        private val instance = ActiivtyStack()
        fun getScreenManager(): ActiivtyStack {
            return instance
        }

    }

    // 弹出当前activity并销毁
    fun popActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activity.finish()
            mActivityStack.remove(activity)
        }
    }

    // 将当前Activity推入栈中
    fun pushActivity(activity: Activity) {
        mActivityStack.add(activity)
    }


    // 退出栈中所有Activity
    fun clearAllActivity() {
        while (!mActivityStack.isEmpty()) {
            val activity = mActivityStack.pop()
            if (activity != null) {
                activity!!.finish()
            }
        }
    }
}