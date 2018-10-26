package hhxk.ygw

import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import com.uuzuche.lib_zxing.activity.CodeUtils
import hhxk.util.ActiivtyStack
import hhxk.util.HeadUrl
import hhxk.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_my_code.*
import org.jetbrains.anko.toast
import java.nio.charset.Charset

/**
 * 我的二维码
 */
class MyCodeActivity : AppCompatActivity() {
    lateinit var dialog: LoadingDialog

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_code)
        ActiivtyStack.getScreenManager().pushActivity(this)
        window.statusBarColor = resources.getColor(R.color.DimGray)
        initView()
        initData()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        dialog=LoadingDialog(this)
        code.setImageBitmap(CodeUtils.createImage("15291879667", 400, 400, BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)))
    }

    fun initData(){
        Http.get {
            url = HeadUrl.url + "/personalCenter"
            params {
                "phone" - HeadUrl.loginName
            }
            onStart { dialog.show() }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                when (j["code"]) {
                    10000 -> {
                        var data = j.getJSONObject("data")
                        uImg.setImageURI(data["photo"].toString())
                        name.text = data["realName"].toString()
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

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
