package hhxk.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class SqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "ygw.db") {
    companion object {
        var instance: SqlHelper? = null
        fun getInstance(ctx: Context): SqlHelper {
            if (instance == null)
                instance = SqlHelper(ctx.applicationContext)
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("userInfo", true,
                "phone" to TEXT,
                "pwd" to TEXT)
        db.createTable("userImg", true,
                "phone" to TEXT,
                "img" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("ygw.db", true)
    }

}

val Context.database: SqlHelper
    get() = SqlHelper.getInstance(applicationContext)