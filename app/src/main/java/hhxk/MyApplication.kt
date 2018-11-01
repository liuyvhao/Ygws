package hhxk

import android.app.Application
import android.text.TextUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.mob.MobSDK
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.auth.LoginInfo
import com.netease.nimlib.sdk.util.NIMUtil
import com.ohmerhe.kolley.request.Http
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import hhxk.util.Preferences


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        ZXingLibrary.initDisplayOpinion(this)
        Http.init(this)
        MobSDK.init(this)
        YgwCache.setContext(this)
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this))

        if (NIMUtil.isMainProcess(this)) {
//            initAVChatKit()
        }

//        AVChatManager.getInstance().observeIncomingCall({
//            val extra = it.extra
////            if (PhoneCallStateObserver.getInstance().getPhoneCallState() != PhoneCallStateObserver.PhoneCallStateEnum.IDLE
////                    || AVChatProfile.getInstance().isAVChatting()
////                    || AVChatManager.getInstance().currentChatId != 0L) {
////                AVChatManager.getInstance().sendControlCommand(it.chatId, AVChatControlCommand.BUSY, null)
////                return
////            }
////            // 有网络来电打开AVChatActivity
////            AVChatProfile.getInstance().setAVChatting(true)
////            AVChatActivity.launch(DemoCache.getContext(), data, AVChatActivity.FROM_BROADCASTRECEIVER)
//            startActivity<AVChatActivity>()
//        },true)
    }

    private fun getLoginInfo(): LoginInfo? {
        val account = Preferences.getUserAccount()
        val token = Preferences.getUserToken()
        return if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            YgwCache.setAccount(account!!.toLowerCase())
            LoginInfo(account, token)
        } else {
            null
        }
    }

//    private fun initAVChatKit() {
//        val avChatOptions = object : AVChatOptions() {
//            fun logout(context: Context) {
//                MainActivity.logout(context, true)
//            }
//        }
//        avChatOptions.entranceActivity = WelcomeActivity::class.java
//        avChatOptions.notificationIconRes = R.drawable.ic_stat_notify_msg
//        AVChatKit.init(avChatOptions)
//
//        // 初始化日志系统
//        LogHelper.init()
//        // 设置用户相关资料提供者
//        AVChatKit.setUserInfoProvider(object : IUserInfoProvider() {
//            fun getUserInfo(account: String): UserInfo {
//                return NimUIKit.getUserInfoProvider().getUserInfo(account)
//            }
//
//            fun getUserDisplayName(account: String): String {
//                return UserInfoHelper.getUserDisplayName(account)
//            }
//        })
//        // 设置群组数据提供者
//        AVChatKit.setTeamDataProvider(object : ITeamDataProvider() {
//            fun getDisplayNameWithoutMe(teamId: String, account: String): String {
//                return TeamHelper.getDisplayNameWithoutMe(teamId, account)
//            }
//
//            fun getTeamMemberDisplayName(teamId: String, account: String): String {
//                return TeamHelper.getTeamMemberDisplayName(teamId, account)
//            }
//        })
//    }

}