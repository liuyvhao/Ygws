package hhxk.ygw

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.YgwCache
import hhxk.util.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.nio.charset.Charset

/**
 * 登录
 */
class LoginActivity : AppCompatActivity() {
    companion object {
        var loginActivity: LoginActivity? = null
    }

    lateinit var dialog: LoadingDialog

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.WHITE
        ActiivtyStack.getScreenManager().pushActivity(this)
        val account = Preferences.getUserAccount()
        val token = Preferences.getUserToken()
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            LoginAgain(account.toString(), token.toString())
        } else {
            setContentView(R.layout.activity_login)
            initView()
        }

    }

    private fun initView() {
        loginActivity = this
        val account = Preferences.getUserAccount()
        val img = Preferences.getUserImg()
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(img)) {
            uImg.setImageURI(img)
            phone.setText(account)
        }
        close.setOnClickListener { finish() }
        phone.setSelection(phone.text.length)
        phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clear.visibility = if (phone.text.isNotEmpty()) View.VISIBLE else View.GONE
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(img)) {
                    if (phone.text.toString() == img)
                        uImg.setImageURI(img)
                }
            }
        })
        pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pwd_clear.visibility = if (pwd.text.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
        clear.setOnClickListener { phone.text.clear() }
        pwd_clear.setOnClickListener { pwd.text.clear() }
        pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        eye.setOnClickListener {
            if (!eye.isChecked) {
                pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                pwd.setSelection(pwd.text.length)
            } else {
                pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                pwd.setSelection(pwd.text.length)
            }
        }
        dialog = LoadingDialog(this)
        login.setOnClickListener {
            if (phone.text.isNotEmpty() && pwd.text.isNotEmpty()) {
                Login()
            }
        }
        register.setOnClickListener { startActivity<RegisterActivity>("phone" to phone.text.toString()) }
        forget.setOnClickListener { startActivity<ForgetActivity>("phone" to phone.text.toString()) }
    }

    /*重新登录*/
    private fun LoginAgain(account: String, pwd: String) {
        Http.get {
            url = YgwCache.url + "/login"
            params {
                "phone" - account
                "password" - pwd
            }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                var code = j["code"]
                when (code) {
                    10000 -> {
//                                var loginInfo = LoginInfo(phone.text.toString(), pwd.text.toString())
//                                NIMClient.getService(AuthService::class.java).login(loginInfo)
                        YgwCache.setAccount(account)
                        YgwCache.setToken(j["data"].toString())

                        startActivity<MainActivity>()
                        overridePendingTransition(R.anim.activity_fade, R.anim.activity_hold)
                        finish()
                    }
                    else -> {
                        setContentView(R.layout.activity_login)
                        initView()
                    }
                }
            }
            onFail {
                setContentView(R.layout.activity_login)
                initView()
            }
        }
    }

    /*登录*/
    private fun Login() {
        Http.get {
            url = YgwCache.url + "/login"
            params {
                "phone" - phone.text.toString()
                "password" - pwd.text.toString()
            }
            onStart { dialog.show() }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                var code = j["code"]
                when (code) {
                    10000 -> {
//                                var loginInfo = LoginInfo(phone.text.toString(), pwd.text.toString())
//                                NIMClient.getService(AuthService::class.java).login(loginInfo)
                        var account = phone.text.toString().trim()
                        var pwd = pwd.text.toString().trim()
                        var token = j["data"].toString()

                        YgwCache.setAccount(account)
                        YgwCache.setToken(token)
                        saveLoginInfo(account, pwd)

                        startActivity<MainActivity>()
                        overridePendingTransition(R.anim.activity_fade, R.anim.activity_hold)
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
    }

    private fun saveLoginInfo(account: String, pwd: String) {
        Preferences.saveUserAccount(account)
        Preferences.saveUserToken(pwd)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
