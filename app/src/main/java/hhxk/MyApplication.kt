package hhxk

import android.app.Application
import android.graphics.Color
import com.facebook.drawee.backends.pipeline.Fresco
import com.mob.MobSDK
//import com.netease.nimlib.sdk.NIMClient
//import com.netease.nimlib.sdk.SDKOptions
//import com.netease.nimlib.sdk.StatusBarNotificationConfig
//import com.netease.nimlib.sdk.avchat.AVChatManager
import com.ohmerhe.kolley.request.Http
import com.uuzuche.lib_zxing.activity.ZXingLibrary

//import android.R.attr.data
//import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand
//import hhxk.ygw.AVChatActivity
//import org.jetbrains.anko.startActivity


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        ZXingLibrary.initDisplayOpinion(this)
        Http.init(this)
        MobSDK.init(this)
//        NIMClient.init(this, null, options())

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

//    fun options(): SDKOptions {
//        var op = SDKOptions()
//        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
//        var config = StatusBarNotificationConfig()
////        config.notificationEntrance=We
//        // 呼吸灯配置
//        config.ledARGB = Color.GREEN
//        config.ledOnMs = 1000
//        config.ledOffMs = 1500
//        return op
//    }
}