package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import hhxk.util.ActiivtyStack
import hhxk.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*
import java.util.regex.Pattern

/**
 * 注册(验证手机号)
 */
class RegisterActivity : AppCompatActivity() {
    lateinit var dialog: LoadingDialog
    lateinit var eventHandler: EventHandler
    private var sendCodeThread: Timer? = null// 验证码计时
    private var countDown = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    private fun initView() {
        back.setOnClickListener { finish() }
        phone.setText(intent.getStringExtra("phone"))
        phone.setSelection(phone.text.length)
        phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clear.visibility = if (phone.text.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
        dialog = LoadingDialog(this)
        clear.setOnClickListener { phone.text.clear() }
        bt_y.setOnClickListener {
            if (phone.text.isNotEmpty()) {
                if (isPhone(phone.text.trim().toString())) {
                    SMSSDK.getVerificationCode("86", phone.text.trim().toString())
                    handleCheckCode()
                } else
                    toast("请填写正确的手机号！")

            } else
                toast("请填写手机号！")
        }
        next.setOnClickListener {
            if (phone.text.isNotEmpty() && yan.text.isNotEmpty()) {
                dialog.show()
                SMSSDK.submitVerificationCode("86", phone.text.trim().toString(), yan.text.trim().toString())
            }
        }
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
                                    startActivity<RegisterPwdActivity>("phone" to phone.text.toString())
                                    overridePendingTransition(R.anim.activity_fade, R.anim.activity_hold)
                                    finish()

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

    /**
     * 验证手机格式
     *
     * @param phone
     * @return
     */
    private fun isPhone(phone: String): Boolean {
        val regex = "^0?1[0-9]{10}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(phone)
        return matcher.matches()
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterEventHandler(eventHandler)
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
