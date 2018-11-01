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
import kotlinx.android.synthetic.main.activity_update_pwd.*
import org.jetbrains.anko.toast
import java.nio.charset.Charset

/**
 * 修改密码
 */
class UpdatePwdActivity : AppCompatActivity() {
    lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pwd)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        dialog = LoadingDialog(this)
        old_clear.setOnClickListener { oldPwd.text.clear() }
        oldPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        oldPwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                old_clear.visibility = if (oldPwd.text.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
        old_eye.setOnClickListener {
            if (!old_eye.isChecked) {
                oldPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                oldPwd.setSelection(oldPwd.text.length)
            } else {
                oldPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                oldPwd.setSelection(oldPwd.text.length)
            }
        }


        new_clear.setOnClickListener { newPwd.text.clear() }
        newPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        newPwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                new_clear.visibility = if (newPwd.text.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
        new_eye.setOnClickListener {
            if (!new_eye.isChecked) {
                newPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                newPwd.setSelection(newPwd.text.length)
            } else {
                newPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                newPwd.setSelection(newPwd.text.length)
            }
        }

        submit.setOnClickListener {
            if (oldPwd.text.isNotEmpty() && newPwd.text.isNotEmpty()) {
                Http.get {
                    url = YgwCache.url + "/updatePassword"
                    params {
                        "phone" - YgwCache.getAccount()!!
                        "oldPwd" - oldPwd.text.trim().toString()
                        "newPwd" - newPwd.text.trim().toString()
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
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
