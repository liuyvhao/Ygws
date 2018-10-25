package hhxk.ygw

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import hhxk.util.ActiivtyStack
import kotlinx.android.synthetic.main.activity_search.*

/**
 * 搜周边
 */
class SearchActivity : AppCompatActivity() {
    private var aMap: AMap?=null
    private var isOne=true

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        mapView.onCreate(savedInstanceState)
        window.statusBarColor=resources.getColor(R.color.white)
        ActiivtyStack.getScreenManager().pushActivity(this)
        initView()
    }

    fun initView(){
        back.setOnClickListener { finish() }

        if (aMap==null)
            aMap=mapView.map

        var myLocationStyle= MyLocationStyle()
        myLocationStyle.interval(2000)
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
        myLocationStyle.showMyLocation(true)
        aMap!!.myLocationStyle=myLocationStyle
        aMap!!.uiSettings.zoomPosition= AMapOptions.LOGO_MARGIN_RIGHT
        aMap!!.isMyLocationEnabled=true
        aMap!!.uiSettings.isMyLocationButtonEnabled = true
        aMap!!.setOnMyLocationChangeListener {
            if (isOne){
                aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(it.latitude,it.longitude)))
                aMap!!.moveCamera(CameraUpdateFactory.zoomTo(17f))
                isOne=false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        ActiivtyStack.getScreenManager().popActivity(this)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
