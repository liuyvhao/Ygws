package hhxk.ygw

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.ohmerhe.kolley.request.Http
import hhxk.YgwCache
import hhxk.util.ActiivtyStack
import hhxk.util.CameraDialog
import hhxk.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_personal.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.io.File
import java.nio.charset.Charset

/**
 * 个人信息
 */
class PersonalActivity : AppCompatActivity() {
    lateinit var dialog: LoadingDialog
    private var uri: Uri? = null
    private val outputImage = File("/sdcard/YG/user_image.jpg")
    private val imageUri = Uri.fromFile(outputImage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
        initData()
    }

    fun initView() {
        back.setOnClickListener { finish() }

        val lt = File("/sdcard/YG/")
        if (!lt.exists()) {
            Log.e("TAG", "第一次创建文件夹")
            lt.mkdirs()// 如果文件夹不存在，则创建文件夹
        }

        img.setOnClickListener {
            var d = CameraDialog(this)
            d.show()
            d.takePicture!!.setOnClickListener {
                val photo = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val out = outputImage
                uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val contentValues = ContentValues(1)
                    contentValues.put(MediaStore.Images.Media.DATA, out.absolutePath)
                    this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                } else {
                    Uri.fromFile(out)
                }
                photo.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(photo, 2)
                d.dismiss()
            }
            d.album!!.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(intent, 1)
                d.dismiss()
            }
        }
        updateName.setOnClickListener { startActivityForResult<UpdateNameActivity>(20, "name" to name.text.toString()) }
        myCode.setOnClickListener { startActivity<MyCodeActivity>() }
        address.setOnClickListener { startActivity<MyAddressActivity>() }
        position.setOnClickListener { startActivity<PositionActivity>() }
        dialog = LoadingDialog(this)
    }

    private fun initData() {
        Http.get {
            url = YgwCache.url + "/personalCenter"
            params {
                "phone" - YgwCache.getAccount()!!
            }
            onStart { dialog.show() }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                when (j["code"]) {
                    10000 -> {
                        var data = j.getJSONObject("data")
                        uImg.setImageURI(data["photo"].toString())
                        name.text = data["realName"].toString()
                        var p = data["phone"].toString()
                        var c = p.substring(3, 9)
                        phone.text = p.replace(c, "******")
                        if (data["identificationNumber"] == null)
                            identity.text = "未认证"
                        else
                            identity.text = "已认证"
                    }
                    else -> {
                        toast(j["message"].toString())
                    }
                }
            }
            onFail { toast("请求失败！") }
            onFinish { dialog.dismiss() }
        }
    }

    private fun uplodImg(pic: String) {
        Http.post {
            url = YgwCache.url + "/updateAvatar"
            headers {
                "Authorization" - YgwCache.getToken()!!
            }
            params {
                "phone" - YgwCache.getAccount()!!
            }
            files {
                "file" - pic
            }
            onStart { dialog.show() }
            onSuccess {
                var str = it.toString(Charset.defaultCharset())
                var j = JSONObject.parseObject(str)
                when (j["code"]) {
                    10000 -> {
                        this@PersonalActivity.setResult(10)
                        initData()
                    }
                    else -> {
                        toast(j["message"].toString())
                    }
                }
            }
            onFail { toast("请求失败！") }
            onFinish { dialog.dismiss() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 25) initData()
        when (requestCode) {
            1 -> {      //相册
                if (data != null) {
                    val intent = Intent("com.android.camera.action.CROP")
                    intent.setDataAndType(data.data, "image/*")
                    intent.putExtra("scale", true)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    startActivityForResult(intent, 10)
                }
            }
            2 -> {      //相机
                if (requestCode == 2) {
                    val file = File(cacheDir, "userImg")
                    if (file.exists()) {
                        val intent = Intent("com.android.camera.action.CROP")
                        intent.setDataAndType(uri, "image/*")
                        intent.putExtra("scale", true)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                        startActivityForResult(intent, 10)
                    }
                }
            }
            10 -> {     //上传
                if (data != null) {
                    var picPath = imageUri.toString()
                    picPath = picPath.replace("file://", "")
                    uplodImg(picPath)
                    this@PersonalActivity.setResult(1)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
