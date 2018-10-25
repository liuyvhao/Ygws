package hhxk.ygw

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.facebook.drawee.view.SimpleDraweeView
import com.ohmerhe.kolley.request.Http
import com.youth.banner.loader.ImageLoader
import hhxk.adapter.NewsAdapter
import hhxk.pojo.News
import hhxk.util.ActiivtyStack
import hhxk.util.HeadUrl
import hhxk.util.OnItemClickListener
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.nio.charset.Charset
import java.util.ArrayList

/**
 * 主页
 */
class HomeActivity : AppCompatActivity() {
    private var imgList = ArrayList<String>()
    var newsList = ArrayList<News>()
    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    private fun initView() {
        scan.setOnClickListener { startActivity<ScanActivity>() }

        banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                imageView!!.setImageURI(Uri.parse(path as String))
            }

            override fun createImageView(context: Context?): ImageView {
                return SimpleDraweeView(context)
            }
        })


        search.setOnClickListener { startActivity<SearchActivity>() }
        myCar.setOnClickListener { startActivity<MyCarActivity>() }
        insurance.setOnClickListener { startActivity<InsuranceActivity>() }
        bill.setOnClickListener { startActivity<BillActivity>() }
        accident.setOnClickListener { startActivity<AccidentActivity>() }
        record.setOnClickListener { startActivity<RecordActivity>() }
        more.setOnClickListener { startActivity<NewsActivity>() }

        adapter = NewsAdapter(newsList, object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                startActivity<NewsInfoActivity>("id" to newsList[position].id)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initData() {
        Http.get {
            url = HeadUrl.url + "/news/getBannerList"
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                when (j["code"]) {
                    10000 -> {
                        var data = JSONArray.parseArray(j["data"].toString())
                        for (i in 0 until data.size) {
                            var banner = JSONObject.parseObject(data[i].toString())
                            imgList.add(banner["path"].toString())
                        }

                        banner.setImages(imgList)
                        banner.setDelayTime(4000)
                        banner.start()
                    }
                    else -> {
                        toast(j["message"].toString())
                    }
                }
            }
            onFail { toast("请求失败！") }
        }

        Http.get {
            url = HeadUrl.url + "/news/getHome"
            params {
                "page" - "1"
                "rows" - "2"
            }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                when (j["code"]) {
                    10000 -> {
                        var data = JSONObject.parseObject(j["data"].toString())
                        var list = JSONArray.parseArray(data["list"].toString())
                        for (i in 0 until list.size) {
                            var new = JSONObject.parseObject(list[i].toString())
                            var imgList = JSONArray.parseArray(new["imgList"].toString())
                            var img = JSONObject.parseObject(imgList[0].toString())

                            newsList.add(News(new["id"].toString(), img["path"].toString(), new["title"].toString(), new["newsSources"].toString(), new["releaseTime"].toString()))
                        }
                        adapter.notifyDataSetChanged()
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
