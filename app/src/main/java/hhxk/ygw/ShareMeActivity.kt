package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import hhxk.adapter.PositionAdapter
import hhxk.pojo.ShareName
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_share_me.*
import java.util.ArrayList

/**
 * 分享给我的
 */
class ShareMeActivity : AppCompatActivity() {
    var positionList = ArrayList<ShareName>()
    lateinit var adapter: PositionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_me)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    fun initView(){
        back.setOnClickListener { finish() }

        adapter = PositionAdapter(positionList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun initData() {
        positionList.add(ShareName("https://img01.sogoucdn.com/app/a/100520093/24f12acffab3dd02-a5f10980b0409d2b-2b85914e6c6550226f4d3e81fab16557.jpg", "肖欢"))
        positionList.add(ShareName("https://i04piccdn.sogoucdn.com/a4acdda54f667c7a","李小龙"))
        positionList.add(ShareName("https://i01piccdn.sogoucdn.com/16e51674bbafe431","邓文涛"))
        positionList.add(ShareName("https://i03piccdn.sogoucdn.com/29312425e75823d3","贺永胜"))
        positionList.add(ShareName("https://i01picsos.sogoucdn.com/eb7a74d52ee20aca","陈秋月"))
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
