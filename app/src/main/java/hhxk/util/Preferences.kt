package hhxk.util

import android.content.Context
import android.content.SharedPreferences
import hhxk.YgwCache

class Preferences {
    companion object {
        private val KEY_USER_ACCOUNT = "account"
        private val KEY_USER_TOKEN = "token"    //网易云token
        private val USER_Img = "img"

        fun saveUserAccount(account: String) {
            saveString(KEY_USER_ACCOUNT, account)
        }

        fun getUserAccount(): String? {
            return getString(KEY_USER_ACCOUNT)
        }

        fun saveUserToken(token: String) {
            saveString(KEY_USER_TOKEN, token)
        }

        fun getUserToken(): String? {
            return getString(KEY_USER_TOKEN)
        }

        fun saveUserImg(img: String) {
            saveString(USER_Img, img)
        }

        fun getUserImg(): String? {
            return getString(USER_Img)
        }

        fun removeToken() {
            clearString(KEY_USER_TOKEN)
        }

        private fun saveString(key: String, value: String) {
            val editor = getSharedPreferences().edit()
            editor.putString(key, value)
            editor.commit()
        }

        private fun getString(key: String): String? {
            return getSharedPreferences().getString(key, null)
        }

        private fun clearString(key: String) {
            val editor = getSharedPreferences().edit()
            editor.remove(key).commit()
        }

        private fun getSharedPreferences(): SharedPreferences {
            return YgwCache.getContext()!!.getSharedPreferences("Ygw", Context.MODE_PRIVATE)
        }
    }
}