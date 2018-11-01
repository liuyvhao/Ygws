package hhxk

import android.content.Context
import com.netease.nimlib.sdk.SDKOptions

class NimSDKOptionConfig {

    companion object {
        fun getSDKOptions(context: Context): SDKOptions {
            val options = SDKOptions()
            return options
        }
    }

}