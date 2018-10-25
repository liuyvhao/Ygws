package hhxk.ygw

import android.Manifest
import android.app.Activity
import android.app.TabActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.widget.RadioGroup
import android.widget.TabHost
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : TabActivity(), RadioGroup.OnCheckedChangeListener {
    lateinit var mTabHost: TabHost
    var mainfestList = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA)

    companion object {
        var mainActivity: Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        for (i in 0 until mainfestList.size) {
            if (ContextCompat.checkSelfPermission(this, mainfestList[i]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, mainfestList, 10)
            }
        }
        mainActivity = this

        //实例化选项卡
        mTabHost = this.tabHost
        //添加选项卡
        mTabHost?.addTab(mTabHost?.newTabSpec("home")?.setIndicator("home")
                ?.setContent(Intent(this, HomeActivity::class.java)))

        mTabHost?.addTab(mTabHost?.newTabSpec("friend")?.setIndicator("friend")
                ?.setContent(Intent(this, FriendActivity::class.java)))

        mTabHost?.addTab(mTabHost?.newTabSpec("center")?.setIndicator("center")
                ?.setContent(Intent(this, CenterActivity::class.java)))

        radioGroup.setOnCheckedChangeListener(this)
        radioGroup.check(R.id.home)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.home -> {
                mTabHost?.setCurrentTabByTag("home")
            }
            R.id.friend -> {
                mTabHost?.setCurrentTabByTag("friend")
            }
            R.id.center -> {
                mTabHost?.setCurrentTabByTag("center")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 10) {

        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK
                && event.action == KeyEvent.ACTION_DOWN
                && event.repeatCount == 0) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }
        return true
    }
}
