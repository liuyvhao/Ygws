package hhxk

import android.content.Context
import com.netease.nimlib.sdk.StatusBarNotificationConfig

class YgwCache {
    companion object {
        private var context: Context? = null
        private var account: String? = null
        private var token: String? = null       //亿耕网token
        private var notificationConfig: StatusBarNotificationConfig? = null
        val url="http://114.55.235.16:9001/ygw/api/front"
        //        var url = "http://192.168.0.117:9001/ygw/api/front"
        var id=""

        fun clear() {
            account = null
        }

        fun getAccount(): String? {
            return account
        }

        private var mainTaskLaunching: Boolean = false

        fun setAccount(account: String) {
            Companion.account = account
//            AVChatKit.setAccount(account)
        }

        fun getToken(): String? {
            return token
        }

        fun setToken(token: String) {
            Companion.token = token
        }

        fun setNotificationConfig(notificationConfig: StatusBarNotificationConfig) {
            Companion.notificationConfig = notificationConfig
        }

        fun getNotificationConfig(): StatusBarNotificationConfig? {
            return notificationConfig
        }

        fun getContext(): Context? {
            return context
        }

        fun setContext(context: Context) {
            Companion.context = context.applicationContext
//            AVChatKit.setContext(context)
        }

        fun setMainTaskLaunching(mainTaskLaunching: Boolean) {
            Companion.mainTaskLaunching = mainTaskLaunching
//            AVChatKit.setMainTaskLaunching(mainTaskLaunching)
        }

        fun isMainTaskLaunching(): Boolean {
            return mainTaskLaunching
        }
    }

}