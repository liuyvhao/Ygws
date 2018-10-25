package hhxk.ygw

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_insert_address.*
import android.content.ContentResolver
import android.net.Uri


/**
 * 新增地址
 */
class InsertAddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_address)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView() {
        back.setOnClickListener { finish() }

        add.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, 10)
        }
        submit.setOnClickListener { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> {
                if (data != null) {
                    var uri = data.data

                    var cursor = contentResolver.query(uri, null, null, null, null)
                    cursor.moveToFirst()
                    var number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    phone.setText(number)
                    var tName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                    name.setText(tName)
                    name.setSelection(name.text.length)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }
}
