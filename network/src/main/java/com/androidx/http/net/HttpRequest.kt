package com.androidx.http.net

import android.os.Handler
import android.os.Looper.getMainLooper
import android.os.Message
import android.util.Log
import com.androidx.http.net.listener.DownloadListener
import com.androidx.http.net.listener.HttpRequestListener
import com.androidx.http.net.module.MsgModule
import com.androidx.reduce.tools.Storage
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.SocketTimeoutException

class HttpRequest : HttpRequestListener {

    private var currentConnect = 0
    private val hnl: HttpNetworkListener = HttpNetwork.builder()
    private val handler = Handler(getMainLooper(), Handler.Callback label@{ message: Message ->
        val msg = message.obj as MsgModule
        when (message.what) {
            -2 -> {
                msg.callback.onFailure(String(msg.msg1))
                return@label false
            }
            -1 -> {
                msg.response.onFailure(msg.msg)
                return@label false
            }
            0 -> {
                msg.response.onSuccess(msg.msg)
                return@label false
            }
            1 -> {
                try {
                    msg.callback.onSuccess(msg.msg1)
                } catch (e: Exception) {
                    Log.e("异常参数", String(msg.msg1))
                    Log.e("参数回调异常", Log.getStackTraceString(e))
                }
                return@label false
            }
            101 -> {
                msg.downloadListener.finish(msg.msg2, msg.msg3)
                return@label false
            }
            102 -> {
                msg.downloadListener.fail(msg.msg)
                return@label false
            }
            103 -> {
                msg.downloadListener.error(msg.msg)
                return@label false
            }
            else -> return@label false
        }
    })

    override fun getRequest(
        url: String,
        map: MutableMap<String, Any>,
        maxAnewCount: Int,
        resonse: com.androidx.http.net.listener.Response?
    ) {
        hnl.getRequest(url, map).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val obj = MsgModule(Log.getStackTraceString(e), resonse!!)
                handler.sendMessage(handler.obtainMessage(-1, obj))
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val obj = response.body?.let { MsgModule(it.string(), resonse!!) }
                handler.sendMessage(handler.obtainMessage(0, obj))
            }
        })
    }

    override fun postRequestProto(
        url: String,
        bytes: ByteArray,
        maxAnewCount: Int,
        callback: com.androidx.http.net.listener.Callback?
    ) {
        hnl.postRequestProto(url, bytes).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val obj = MsgModule(Log.getStackTraceString(e).toByteArray(), callback!!)
                handler.sendMessage(handler.obtainMessage(-2, obj))
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val obj = response.body?.let { MsgModule(it.bytes(), callback!!) }
                handler.sendMessage(handler.obtainMessage(1, obj))
            }
        })
    }

    override fun postRequest(
        url: String,
        map: MutableMap<String, Any>,
        maxAnewCount: Int,
        resonse: com.androidx.http.net.listener.Response?
    ) {
        hnl.postRequest(url, map).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val obj = MsgModule(Log.getStackTraceString(e), resonse!!)
                handler.sendMessage(handler.obtainMessage(-1, obj))
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val obj = response.body?.let { MsgModule(it.string(), resonse!!) }
                handler.sendMessage(handler.obtainMessage(0, obj))
            }
        })
    }

    override fun postRequest(
        url: String,
        json: JsonObject,
        maxAnewCount: Int,
        resonse: com.androidx.http.net.listener.Response?
    ) {
        hnl.postRequest(url, json).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val obj = MsgModule(Log.getStackTraceString(e), resonse!!)
                handler.sendMessage(handler.obtainMessage(-1, obj))
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val obj = response.body?.let { MsgModule(it.string(), resonse!!) }
                handler.sendMessage(handler.obtainMessage(0, obj))
            }
        })
    }

    override fun deleteRequest(
        url: String,
        json: JsonObject,
        maxAnewCount: Int,
        resonse: com.androidx.http.net.listener.Response?
    ) {
        hnl.deleteRequest(url, json).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val obj = MsgModule(Log.getStackTraceString(e), resonse!!)
                handler.sendMessage(handler.obtainMessage(-1, obj))
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val obj = response.body?.let { MsgModule(it.string(), resonse!!) }
                handler.sendMessage(handler.obtainMessage(0, obj))
            }
        })
    }

    override fun deleteRequest(
        url: String,
        map: Map<String, Any>,
        maxAnewCount: Int,
        resonse: com.androidx.http.net.listener.Response?
    ) {
        hnl.deleteRequest(url, map).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val obj = MsgModule(Log.getStackTraceString(e), resonse!!)
                handler.sendMessage(handler.obtainMessage(-1, obj))
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val obj = response.body?.let { MsgModule(it.string(), resonse!!) }
                handler.sendMessage(handler.obtainMessage(0, obj))
            }
        })
    }

    override fun forrequest(
        url: String,
        key: String,
        json: JsonObject,
        maxAnewCount: Int,
        resonse: com.androidx.http.net.listener.Response?
    ) {
        hnl.forrequest(url, key, json).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val obj = MsgModule(Log.getStackTraceString(e), resonse!!)
                handler.sendMessage(handler.obtainMessage(-1, obj))
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val obj = response.body?.let { MsgModule(it.string(), resonse!!) }
                handler.sendMessage(handler.obtainMessage(0, obj))
            }
        })
    }

    @Strictfp
    override fun upload(
        url: String,
        param: Map<String, Any>,
        key: String,
        path: String,
        maxAnewCount: Int,
        resonse: com.androidx.http.net.listener.Response?
    ) {
        hnl.uploadRequest(url, param, key, path).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val obj = MsgModule(Log.getStackTraceString(e), resonse!!)
                handler.sendMessage(handler.obtainMessage(-1, obj))
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            @Throws(Exception::class)
            override fun onResponse(call: Call, response: Response) {
                val obj = response.body?.let { MsgModule(it.string(), resonse!!) }
                handler.sendMessage(handler.obtainMessage(0, obj))
            }
        })
    }

    @Strictfp
    override fun download(
        url: String,
        outPath: String,
        maxAnewCount: Int,
        listener: DownloadListener
    ) {
        val start = System.currentTimeMillis()
        listener.start()
        hnl.downloadRequest(url, listener).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val m = MsgModule(Log.getStackTraceString(e), listener)
                val msg = handler.obtainMessage(102, m)
                handler.sendMessage(msg)
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    hnl.getCall(call.request()).enqueue(this)
                } else if (e is SocketTimeoutException && -1 == maxAnewCount) {
                    hnl.getCall(call.request()).enqueue(this)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val bs: InputStream = response.body!!.byteStream()
                    val end = System.currentTimeMillis()
                    val file: File = Storage.write(outPath, bs)
                    val obj = response.body?.let { MsgModule(file, (end - start * 1.0), listener) }
                    val msg = handler.obtainMessage(101, obj)
                    handler.sendMessage(msg)
                } catch (e: Exception) {
                    val m = MsgModule(Log.getStackTraceString(e), listener)
                    val msg = handler.obtainMessage(103, m)
                    handler.sendMessage(msg)
                }
            }
        })
    }


}