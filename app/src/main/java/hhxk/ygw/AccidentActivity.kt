package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import hhxk.adapter.PhotoWallAdapter
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_accident.*
import java.util.ArrayList

/**
 * 事故上报
 */
class AccidentActivity : AppCompatActivity() {
    private var imgList = ArrayList<String>()
    lateinit var adapter: PhotoWallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accident)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        img.setImageURI("https://img04.sogoucdn.com/app/a/100520093/ac75323d6b6de243-cb085b110105c1b4-57a373f768bc42dc68b50c0767efcf74.jpg")

        adapter = PhotoWallAdapter(imgList)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
        imgList.add("https://i01piccdn.sogoucdn.com/df193d8545b11806")
        imgList.add("https://i03piccdn.sogoucdn.com/297a5cb3a930c245")
        imgList.add("https://i04piccdn.sogoucdn.com/f4eae67c8942ceff")
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
