package hhxk.ygw

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.util.ActiivtyStack
import hhxk.util.HeadUrl
import hhxk.util.database
import kotlinx.android.synthetic.main.activity_center.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.nio.charset.Charset

/**
 * 我的
 */
class CenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    private fun initView() {
        setting.setOnClickListener { startActivity<SettingActivity>() }
        personal.setOnClickListener { startActivityForResult<PersonalActivity>(5) }
        terminal.setOnClickListener { startActivity<TerminalActivity>() }
        bill.setOnClickListener { startActivity<BillActivity>() }
        myCar.setOnClickListener { startActivity<MyCarActivity>() }
        collection.setOnClickListener { startActivity<CollectionActivity>() }
    }

    private fun initData() {
        Http.get {
            url = HeadUrl.url + "/personalCenter"
            params {
                "phone" - HeadUrl.loginName
            }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                when (j["code"]) {
                    10000 -> {
                        var data = j.getJSONObject("data")
                        HeadUrl.id = data["id"].toString()
                        var img = data["photo"].toString()
                        uImg.setImageURI(img)
                        name.text = data["realName"].toString()
                        var p = data["phone"].toString()
                        var c = p.substring(3, 9)
                        phone.text = p.replace(c, "******")

                        database.use {
                            select("userImg").whereSimple("phone=?", p).exec {
                                if (count == 0) {
                                    insert("userImg",
                                            "phone" to p,
                                            "img" to img)
                                } else
                                    update("userImg", "img" to img)
                            }
                        }
                    }
                    else -> {
                        toast(j["message"].toString())
                    }
                }
            }
            onFail { toast("请求失败！") }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            10 -> {
                initData()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
