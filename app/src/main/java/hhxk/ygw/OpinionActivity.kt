package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.util.ActiivtyStack
import hhxk.util.HeadUrl
import hhxk.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_opinion.*
import org.jetbrains.anko.toast
import java.nio.charset.Charset

/**
 * 意见反馈
 */
class OpinionActivity : AppCompatActivity() {
    lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opinion)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }

        dialog = LoadingDialog(this)
        submit.setOnClickListener {
            if (message.text.trim().length < 8) {
                toast("内容不能低于8个字哦！")
            } else {
                var type = if (radio_b.isChecked) 0 else 1
                Http.post {
                    url = HeadUrl.url + "/complaintproposal/insert"
                    headers {
                        "Authorization" - HeadUrl.token
                    }
                    params {
                        "userId" - HeadUrl.id
                        "type" - type.toString()
                        "content" - message.text.toString()
                    }
                    onStart { dialog.show() }
                    onSuccess {
                        var str = it.toString(Charset.defaultCharset())
                        var j = JSONObject.parseObject(str)
                        when (j["code"]) {
                            10000 -> {
                                toast("反馈成功！")
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
