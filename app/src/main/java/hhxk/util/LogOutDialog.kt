package hhxk.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import hhxk.ygw.R
import kotlinx.android.synthetic.main.dialot_logout.*
import org.jetbrains.anko.find

class LogOutDialog(context: Context) : Dialog(context, R.style.ActionSheetDialogStyle) {
    var yes:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialot_logout)
        this.window.setGravity(Gravity.BOTTOM)
        yes=find(R.id.yes)
        no.setOnClickListener { dismiss() }
    }

}