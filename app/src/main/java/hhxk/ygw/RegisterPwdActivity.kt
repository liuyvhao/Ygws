package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.YgwCache
import hhxk.util.ActiivtyStack
import hhxk.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_register_pwd.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.nio.charset.Charset

/**
 * 注册(填写密码)
 */
class RegisterPwdActivity : AppCompatActivity() {
    lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_pwd)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    private fun initView() {
        back.setOnClickListener { finish() }
        phone.text = intent.getStringExtra("phone")
        dialog = LoadingDialog(this)
        pwd_clear.setOnClickListener { pwd.text.clear() }
        pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pwd_clear.visibility = if (pwd.text.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
        eye.setOnClickListener {
            if (!eye.isChecked) {
                pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                pwd.setSelection(pwd.text.length)
            } else {
                pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                pwd.setSelection(pwd.text.length)
            }
        }
        submit.setOnClickListener {
            if (pwd.text.isNotEmpty()) {
                Http.post {
                    url = YgwCache.url + "/register"
                    params {
                        "phone" - phone.text.toString()
                        "password" - pwd.text.toString()
                    }
                    onStart { dialog.show() }
                    onSuccess {
                        var str = it.toString(Charset.defaultCharset())
                        var j = JSONObject.parseObject(str)
                        when (j["code"]) {
                            10000 -> {
                                startActivity<MainActivity>()
                                overridePendingTransition(R.anim.activity_fade, R.anim.activity_hold)
                                finish()
                                LoginActivity.loginActivity!!.finish()
                            }
                            else -> {
                                toast(j["message"].toString())
                            }
                        }
                    }
                    onFail { toast("请求失败！") }
                    onFinish { dialog.dismiss() }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
