package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.YgwCache
import hhxk.util.ActiivtyStack
import hhxk.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_update_name.*
import org.jetbrains.anko.toast
import java.nio.charset.Charset

/**
 * 修改昵称
 */
class UpdateNameActivity : AppCompatActivity() {
    lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_name)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        name.setText(intent.getStringExtra("name"))
        name.setSelection(name.text.length)
        clear.visibility = if (name.text.isNotEmpty()) View.VISIBLE else View.GONE
        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clear.visibility = if (name.text.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
        clear.setOnClickListener { name.text.clear() }

        dialog = LoadingDialog(this)
        submit.setOnClickListener {
            if (name.text.trim() != null) {
                Http.get {
                    url = YgwCache.url + "/updateName"
                    params {
                        "phone" - YgwCache.getAccount()!!
                        "name" - name.text.trim().toString()
                    }
                    onStart { dialog.show() }
                    onSuccess {
                        var str = it.toString(Charset.defaultCharset())
                        var j = JSONObject.parseObject(str)
                        when (j["code"]) {
                            10000 -> {
                                this@UpdateNameActivity.setResult(25)
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
