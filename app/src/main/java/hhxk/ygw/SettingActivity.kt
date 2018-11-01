package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import hhxk.YgwCache
import hhxk.util.*
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

/**
 * 设置
 */
class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        var p = YgwCache.getAccount()!!
        var c = p.substring(3, 9)
        phone.text = p.replace(c, "******")

        updatePhone.setOnClickListener { startActivity<UpdatePhoneActivity>() }
        updatePwd.setOnClickListener { startActivity<UpdatePwdActivity>() }
        opinion.setOnClickListener { startActivity<OpinionActivity>() }
        logOut.setOnClickListener {
            var dialog = LogOutDialog(this)
            dialog.show()
            dialog.yes!!.setOnClickListener {
                startActivity<LoginActivity>()
                Preferences.removeToken()
                finish()
                MainActivity.mainActivity!!.finish()
            }
            var dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            dialog.window.setLayout(dm.widthPixels, dialog.window.attributes.height)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
