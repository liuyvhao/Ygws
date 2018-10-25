package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import hhxk.adapter.TablayoutAdapter
import hhxk.newsfragment.ColumnFragment
import hhxk.newsfragment.HotFragment
import hhxk.newsfragment.PublishFragment
import hhxk.newsfragment.SafetyFragment
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_news.*

/**
 * 新闻
 */
class NewsActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    var titles = arrayOf("热点业务", "专题栏目", "安全宣传", "信息公布")
    private val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }

        for (item in titles)
            tabLayout.addTab(tabLayout.newTab().setText(item))
        tabLayout.setOnTabSelectedListener(this)
        fragments.add(HotFragment())
        fragments.add(ColumnFragment())
        fragments.add(SafetyFragment())
        fragments.add(PublishFragment())
        view_pager.adapter = TablayoutAdapter(supportFragmentManager, fragments, titles)
        tabLayout.setupWithViewPager(view_pager)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null) view_pager.currentItem = tab.position
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
