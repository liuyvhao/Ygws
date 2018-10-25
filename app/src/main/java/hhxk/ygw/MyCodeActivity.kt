package hhxk.ygw

import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.uuzuche.lib_zxing.activity.CodeUtils
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_my_code.*

/**
 * 我的二维码
 */
class MyCodeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_code)
        ActiivtyStack.getScreenManager().pushActivity(this)
        window.statusBarColor = resources.getColor(R.color.DimGray)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }
        uImg.setImageURI("https://i01piccdn.sogoucdn.com/6c772fd3a9a2f3bf")
        code.setImageBitmap(CodeUtils.createImage("15291879667", 400, 400, BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)))
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
