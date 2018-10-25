package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import hhxk.adapter.RecordAdapter
import hhxk.pojo.Record
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_record.*
import java.util.ArrayList

/**
 * 测亩记录
 */
class RecordActivity : AppCompatActivity() {
    var recordList = ArrayList<Record>()
    lateinit var adapter: RecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    fun initView() {
        back.setOnClickListener { finish() }

        adapter = RecordAdapter(recordList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initData() {
        recordList.add(Record("陕A-PU456"))
        recordList.add(Record("陕A-PU456"))
        recordList.add(Record("陕A-PU456"))
        recordList.add(Record("陕A-PU456"))

        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
