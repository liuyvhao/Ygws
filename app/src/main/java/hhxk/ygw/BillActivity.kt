package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import hhxk.adapter.BillAdapter
import hhxk.pojo.Bill
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_bill.*
import java.util.ArrayList

/**
 * 我的保单
 */
class BillActivity : AppCompatActivity() {
    var billList = ArrayList<Bill>()
    lateinit var adapter: BillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    fun initView() {
        back.setOnClickListener { finish() }

        adapter = BillAdapter(billList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initData(){
        billList.add(Bill("https://img03.sogoucdn.com/app/a/100520093/ca86e620b9e623ff-e7ae36db714776c0-a5b56af2afa64143802368e538db92f7.jpg","农机损失互保"))
        billList.add(Bill("https://img03.sogoucdn.com/app/a/100520093/ca86e620b9e623ff-e7ae36db714776c0-a5b56af2afa64143802368e538db92f7.jpg","机上人员互险"))
        billList.add(Bill("https://img03.sogoucdn.com/app/a/100520093/ca86e620b9e623ff-e7ae36db714776c0-a5b56af2afa64143802368e538db92f7.jpg","第三者责任险"))

        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
