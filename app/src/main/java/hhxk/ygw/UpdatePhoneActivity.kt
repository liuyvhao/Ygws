package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import hhxk.util.ActiivtyStack
import hhxk.util.HeadUrl
import kotlinx.android.synthetic.main.activity_update_phone.*
import org.jetbrains.anko.startActivity

/**
 * 更换手机号(确认)
 */
class UpdatePhoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_phone)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView(){
        back.setOnClickListener { finish() }
        var p = HeadUrl.loginName
        var c = p.substring(3, 9)
        phone.text = p.replace(c, "******")

        next.setOnClickListener {
            startActivity<NewPhoneActivity>()
            overridePendingTransition(R.anim.activity_fade, R.anim.activity_hold)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
