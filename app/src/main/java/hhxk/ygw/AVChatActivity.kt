package hhxk.ygw

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import hhxk.util.ActiivtyStack

/**
 * 视频请求界面
 */
class AVChatActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avchat)
        ActiivtyStack.getScreenManager().pushActivity(this)
        window.statusBarColor = resources.getColor(R.color.DimGray)
        initView()
    }

    fun initView() {

    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
