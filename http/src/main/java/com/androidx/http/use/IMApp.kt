package com.androidx.http.use

import android.app.Application
import android.content.Context
import com.androidx.reduce.tools.This
import com.androidx.reduce.tools.Toasts

open class IMApp : Application() {

    companion object {
        @JvmStatic
        lateinit var appThis: This

        @JvmStatic
        lateinit var appToast: Toasts
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        appThis = This.builder()
        appToast = Toasts.builder(base)
    }
}