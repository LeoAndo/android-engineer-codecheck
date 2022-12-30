package jp.co.yumemi.android.codecheck.data.api

import android.util.Log
import io.ktor.client.plugins.logging.*
import jp.co.yumemi.android.codecheck.BuildConfig

class AppHttpLogger : Logger {
    override fun log(message: String) {
        if (BuildConfig.DEBUG) {
            Log.w("AppHttpLogger", message)
        }
    }
}