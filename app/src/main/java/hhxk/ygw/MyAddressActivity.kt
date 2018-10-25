package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import hhxk.adapter.AddressAdapter
import hhxk.pojo.Address
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_my_address.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

/**
 * 我的地址
 */
class MyAddressActivity : AppCompatActivity() {
    var addressList = ArrayList<Address>()
    lateinit var adapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_address)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        insert.setOnClickListener { startActivity<InsertAddressActivity>() }

        adapter = AddressAdapter(addressList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initData() {
        addressList.add(Address("刘宇豪", "15291879667", "陕西西安市雁塔区锦业一路10号中投国际A座1906"))
        addressList.add(Address("刘宇豪", "15291879667", "陕西西安市雁塔区新汇大厦A座903"))

        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }

}
