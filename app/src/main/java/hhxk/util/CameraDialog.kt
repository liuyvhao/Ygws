package hhxk.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.RelativeLayout
import hhxk.ygw.R
import kotlinx.android.synthetic.main.dialog_camera.*
import org.jetbrains.anko.find

class CameraDialog(context: Context) : Dialog(context, R.style.ActionSheetDialogStyle) {
    var takePicture: RelativeLayout? = null
    var album: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_camera)
        takePicture = find(R.id.takePicture)
        album = find(R.id.album)
        var lp = window.attributes
        lp.y = 20
        window.attributes = lp
        this.window.setGravity(Gravity.BOTTOM)
        cancel.setOnClickListener { dismiss() }
    }
}