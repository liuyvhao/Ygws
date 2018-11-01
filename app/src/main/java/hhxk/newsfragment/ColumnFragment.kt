package hhxk.newsfragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.adapter.NewsAdapter
import hhxk.pojo.News
import hhxk.util.OnItemClickListener
import hhxk.ygw.NewsInfoActivity
import hhxk.ygw.R
import hhxk.YgwCache
import kotlinx.android.synthetic.main.fragment_column.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.nio.charset.Charset
import java.util.ArrayList

/**
 * 专题栏目
 */
class ColumnFragment : Fragment() {
    private var v: View? = null
    var newsList = ArrayList<News>()
    lateinit var adapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_column, container, false)
        initView()
        initData()
        return v
    }

    fun initView() {
        adapter = NewsAdapter(newsList, object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                startActivity<NewsInfoActivity>("id" to newsList[position].id)
            }
        })
        v!!.recyclerView.layoutManager = LinearLayoutManager(v!!.context)
        v!!.recyclerView.adapter = adapter
    }

    fun initData() {
        Http.get {
            url = YgwCache.url + "/news/getNewsList"
            params {
                "type" - "2"
                "page" - "1"
                "rows" - "20"
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
}