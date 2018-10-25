package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.util.ActiivtyStack
import hhxk.util.HeadUrl
import hhxk.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_terminal.*
import org.jetbrains.anko.toast
import java.nio.charset.Charset
import java.util.*

/**
 * 我的终端
 */
class TerminalActivity : AppCompatActivity() {
    lateinit var dialog: LoadingDialog
    lateinit var eventHandler: EventHandler
    private var sendCodeThread: Timer? = null// 验证码计时
    private var countDown = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terminal)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        var p = HeadUrl.loginName
        var c = p.substring(3, 9)
        phone.text = p.replace(c, "******")

        eventHandler = object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                super.afterEvent(event, result, data)
                runOnUiThread {
                    dialog.dismiss()
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        when (event) {
                            SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE -> {
                                //提交验证码成功
                                if (result == SMSSDK.RESULT_COMPLETE) {
                                    //处理验证码验证通过的结果
                                    Http.get {
                                        url = HeadUrl.url + "/updateTimPwd"
                                        params {
                                            "phone" - p
                                            "temId" - number.text.toString().trim()
                                            "temPwd" - pwd.text.toString().trim()
                                        }
                                        onStart { dialog.show() }
                                        onSuccess {
                                            var str = it.toString(Charset.defaultCharset())
                                            var j = JSONObject.parseObject(str)
                                            when (j["code"]) {
                                                10000 -> {
                                                    toast("修改成功！")
                                                    finish()
                                                }
                                                else -> {
                                                    toast(j["message"].toString())
                                                }
                                            }
                                        }
                                        onFail { toast("请求失败！") }
                                        onFinish { dialog.dismiss() }
                                    }

                                } else {
                                    toast("验证失败！")
                                }
                            }
                            SMSSDK.EVENT_GET_VERIFICATION_CODE -> {
                                //获取验证码成功
                            }
                            SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES -> {
                                //返回支持发送验证码的国家列表
                            }
                        }
                    }
                }
            }
        }
        SMSSDK.registerEventHandler(eventHandler)



        bt_y.setOnClickListener {
            SMSSDK.getVerificationCode("86", p)
            handleCheckCode()
        }
        dialog = LoadingDialog(this)
        submit.setOnClickListener {
            if (number.text.trim().toString() != null && pwd.text.trim().toString() != null) {
                if (yan.text.isNotEmpty()) {
                    dialog.show()
                    SMSSDK.submitVerificationCode("86", p, yan.text.trim().toString())
                }
            }
        }
    }

    private fun handleCheckCode() {
        bt_y.isClickable = false
        bt_y.setBackgroundResource(R.drawable.bt_send_b)
        if (sendCodeThread == null)
            sendCodeThread = Timer()
        sendCodeThread!!.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (countDown == 0) {
                        // 计时完成
                        sendCodeThread!!.cancel()
                        sendCodeThread = null
                        countDown = 60
                        bt_y.text = "获取验证码"
                        bt_y.isClickable = true
                        bt_y.setBackgroundResource(R.drawable.btn_login_l)
                    } else {
                        bt_y.text = ("剩余"
                                + countDown
                                + "s")
                        countDown--
                    }
                }
            }
        }, 0, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterEventHandler(eventHandler)
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
