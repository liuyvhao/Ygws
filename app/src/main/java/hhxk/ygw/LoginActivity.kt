package hhxk.ygw

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
//import com.netease.nimlib.sdk.NIMClient
//import com.netease.nimlib.sdk.auth.AuthService
//import com.netease.nimlib.sdk.auth.LoginInfo
import com.ohmerhe.kolley.request.Http
import hhxk.pojo.UserImg
import hhxk.pojo.UserInfo
import hhxk.util.ActiivtyStack
import hhxk.util.HeadUrl
import hhxk.util.LoadingDialog
import hhxk.util.database
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
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
    lateinit var imgs: List<UserImg>

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.WHITE
        ActiivtyStack.getScreenManager().pushActivity(this)
        database.use {
            var userInfo = select("userInfo").parseList(object : MapRowParser<UserInfo> {
                override fun parseRow(columns: Map<String, Any?>): UserInfo {
                    var phone = columns["phone"] as String
                    var pwd = columns["pwd"] as String
                    return UserInfo(phone, pwd)
                }
            })

            if (userInfo.isNotEmpty()) {
                Http.get {
                    url = HeadUrl.url + "/login"
                    params {
                        "phone" - userInfo[0].phone
                        "password" - userInfo[0].pwd
                    }
                    onSuccess {
                        var str = it.toString(Charset.defaultCharset())
                        var j = JSONObject.parseObject(str)
                        var code = j["code"]
                        when (code) {
                            10000 -> {
//                                var loginInfo = LoginInfo(phone.text.toString(), pwd.text.toString())
//                                NIMClient.getService(AuthService::class.java).login(loginInfo)
                                HeadUrl.loginName = userInfo[0].phone
                                HeadUrl.token = j["data"].toString()

                                startActivity<MainActivity>()
                                overridePendingTransition(R.anim.activity_fade, R.anim.activity_hold)
                                finish()
                            }
                            else -> {
                                database.use {
                                    execSQL("delete from userInfo where phone='" + userInfo[0].phone + "'")
                                }
                                setContentView(R.layout.activity_login)
                                initView()
                            }
                        }
                    }
                    onFail {
                        database.use {
                            execSQL("delete from userInfo where phone='" + userInfo[0].phone + "'")
                        }
                        setContentView(R.layout.activity_login)
                        initView()
                    }
                }

            } else {
                setContentView(R.layout.activity_login)
                initView()
            }
        }
    }

    private fun initView() {
        loginActivity = this
        database.use {
            imgs = select("userImg").parseList(object : MapRowParser<UserImg> {
                override fun parseRow(columns: Map<String, Any?>): UserImg {
                    var phone = columns["phone"] as String
                    var img = columns["img"] as String
                    return UserImg(phone, img)
                }
            })
            if (imgs.isNotEmpty()) {
                uImg.setImageURI(imgs[0].img)
                phone.setText(imgs[0].phone)
            } else {
                uImg.setImageURI("https://i01piccdn.sogoucdn.com/6c772fd3a9a2f3bf")
            }
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
                if (imgs.isNotEmpty()) {
                    if (phone.text.toString() == imgs[0].phone)
                        uImg.setImageURI(imgs[0].img)
                    else
                        uImg.setImageURI("https://i01piccdn.sogoucdn.com/6c772fd3a9a2f3bf")
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
                Http.get {
                    url = HeadUrl.url + "/login"
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
                                HeadUrl.loginName = phone.text.toString()
                                HeadUrl.token = j["data"].toString()

                                database.use {
                                    select("userInfo").whereSimple("phone=?", phone.text.trim().toString()).exec {
                                        if (count == 0) {
                                            insert("userInfo",
                                                    "phone" to phone.text.trim().toString(),
                                                    "pwd" to pwd.text.trim().toString())
                                        } else
                                            update("userInfo", "pwd" to pwd.text.trim().toString())
                                    }
                                }

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
        }
        register.setOnClickListener { startActivity<RegisterActivity>("phone" to phone.text.toString()) }
        forget.setOnClickListener { startActivity<ForgetActivity>("phone" to phone.text.toString()) }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
