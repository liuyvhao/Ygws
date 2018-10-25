package hhxk.ygw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import hhxk.adapter.FriendMessageAdapter
import hhxk.pojo.FMessage
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_friend.*
import java.util.ArrayList

/**
 * 朋友
 */
class FriendActivity : AppCompatActivity() {
    var fMessageList = ArrayList<FMessage>()
    lateinit var adapter: FriendMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    private fun initView() {
        adapter = FriendMessageAdapter(fMessageList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initData(){
        fMessageList.add(FMessage("https://i03piccdn.sogoucdn.com/058f9f9e6b395ff8","11:00","手机充值","【开学季】还记得曾经和班主任斗智斗勇的时候吗？"))
        fMessageList.add(FMessage("https://i02picsos.sogoucdn.com/4c9fdcc00c8e3ac2","昨天","优酷VIP会员","昆凌晒比基尼美照周董却遭粉丝调侃"))
        fMessageList.add(FMessage("https://i04piccdn.sogoucdn.com/35c5b9e0c4e4d9b4","8月25日","Arvin","又有小鸡来吃饲料了"))
        fMessageList.add(FMessage("https://i02piccdn.sogoucdn.com/0bba3cb694af2035","8月24日","谁耳语","[连接]我才加了“4999元现金接力抽”!"))
        fMessageList.add(FMessage("https://i03piccdn.sogoucdn.com/2c78c13ed61f1d73","8月16日","ELEI","我通过了你的朋友验证请求，现在我们可以开始聊天了。"))
        fMessageList.add(FMessage("https://i03piccdn.sogoucdn.com/e6dea907651c0787","7月24日","不怕开水烫","[转账]转账40.00元"))
        fMessageList.add(FMessage("https://i03piccdn.sogoucdn.com/08f5ab1526db0794","6月11日","心如止水","[转账]转账28.00元"))
        fMessageList.add(FMessage("https://i02picsos.sogoucdn.com/9fafa92b69eef4f9","3月21日","李小龙","[转账]转账36.00元"))
        fMessageList.add(FMessage("https://i01piccdn.sogoucdn.com/c0859cad35948d25","2月18日","BeYoung","[连接]我才加了“4999元现金接力抽”!"))
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
