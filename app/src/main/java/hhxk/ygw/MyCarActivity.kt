package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.YgwCache
import hhxk.adapter.CarAdapter
import hhxk.pojo.Car
import hhxk.util.ActiivtyStack
import hhxk.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_my_car.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.nio.charset.Charset
import java.util.ArrayList

/**
 * 我的农机
 */
class MyCarActivity : AppCompatActivity() {
    private var carList = ArrayList<Car>()
    lateinit var adapter: CarAdapter
    lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_car)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        add.setOnClickListener { startActivity<AddCarActivity>() }

        adapter = CarAdapter(carList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        dialog = LoadingDialog(this)
    }

    private fun initData() {
        Http.get {
            url = YgwCache.url + "/vehicleinformation/list"
            headers {
                "Authorization" - YgwCache.getToken()!!
            }
            params {
                "id" - YgwCache.id
                "page" - "1"
                "limit" - "20"
            }
            onStart { dialog.show() }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                when (j["code"]) {
                    10000 -> {
                        var data = JSONObject.parseObject(j["data"].toString())
                        var list = JSONArray.parseArray(data["list"].toString())
                        for (i in 0 until list.size) {
                            var vehicle = JSONObject.parseObject(list[i].toString())
                            carList.add(Car(vehicle["id"].toString(),vehicle["vehicleImgs"].toString(),vehicle["plateNumber"].toString(),vehicle["documentAddress"].toString()))
                        }

                        adapter.notifyDataSetChanged()
                    }
                    else -> {
                        toast(j["message"].toString())
                    }
                }
            }
            onFail { toast("请求失败！") }
            onFinish { dialog.dismiss() }
        }

//        carList.add(Car("https://i01picsos.sogoucdn.com/738cfb7476a6f66f", "陕A-D0798", "渭南临渭区"))
//        carList.add(Car("https://i02piccdn.sogoucdn.com/6a84d2af8d1cd9d3", "陕A-D5138", "渭南临渭区"))
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
