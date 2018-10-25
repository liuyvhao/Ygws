package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.util.ActiivtyStack
import hhxk.util.HeadUrl
import kotlinx.android.synthetic.main.activity_news_info.*
import org.jetbrains.anko.toast
import java.nio.charset.Charset

/**
 * 新闻详情
 */
class NewsInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_info)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    fun initView() {
        back.setOnClickListener { finish() }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress==100)
                    progressBar.visibility=View.GONE
                else{
                    if (progressBar.visibility==View.GONE)
                        progressBar.visibility=View.VISIBLE
                    progressBar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }
    }

    private fun initData() {
        Http.get {
            url = HeadUrl.url + "/news/getNewsById"
            headers {
                "Authorization" - HeadUrl.token
            }
            params {
                "id" - intent.getStringExtra("id")
            }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                when (j["code"]) {
                    10000 -> {
                        var data = JSONObject.parseObject(j["data"].toString())
                        var content = data["content"].toString()
                        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
                    }
                    else -> {
                        toast(j["message"].toString())
                    }
                }
            }
            onFail { toast("请求失败！") }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
