package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_insurance.*

/**
 * 在线投保
 */
class InsuranceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insurance)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView(){
        back.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
