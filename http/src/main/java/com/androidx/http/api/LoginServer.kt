package com.androidx.http.api

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.provider.Settings
import android.provider.Telephony.Carriers
import android.provider.Telephony.Mms.Addr
import com.androidx.http.net.WebSocketRequest
import com.androidx.http.use.NetRequest
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LoginServer : Service() {

    private var host: String? = null
    private var port: String? = null
    private var user: String? = null
    private var pass: String? = null
    private var project: String? = null
    private var isHttps = false
    private var count: Long = 0//默认15秒间隔重连时间

    @Volatile
    private var exec: ExecutorService? = null
    private lateinit var wakeLock: WakeLock
    private var enqueue: WebSocketRequest? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("InvalidWakeLockTag")
    override fun onCreate() {
        super.onCreate()
        exec = Executors.newCachedThreadPool()
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag")
        wakeLock.acquire(24 * 60 * 60 * 1000L /*24 h*/)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        host = intent.getStringExtra(Addr.ADDRESS)
        port = intent.getStringExtra(Carriers.PORT)
        user = intent.getStringExtra(Carriers.USER)
        pass = intent.getStringExtra(Carriers.PASSWORD)
        project = intent.getStringExtra(Carriers.NAME)
        count = intent.getLongExtra(Addr.TYPE, 15000)
        isHttps = intent.getBooleanExtra(Settings.AUTHORITY, false)
        if (user == null && pass == null) {
            return super.onStartCommand(intent, flags, startId)
        }
        exec!!.execute {
            enqueue = WebSocketRequest.builder()
                .setHost(if (host == null) "" else host)
                .setPort(if (port == null) "" else port)
                .setUser(if (user == null) "" else user)
                .setPass(if (pass == null) "" else pass)
                .setProject(if (project == null) "" else "$project/")
                .isHttps(isHttps)
                .setReconnectInterval(count)
                .build()
            NetRequest.enqueue = enqueue
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        wakeLock.release()
        if (enqueue != null) {
            enqueue!!.close()
        }
        if (!exec!!.isShutdown) {
            exec!!.shutdown()
        }
    }
}