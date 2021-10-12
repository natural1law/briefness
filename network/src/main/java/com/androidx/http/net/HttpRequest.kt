package com.androidx.http.net

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.androidx.http.net.listener.BytesCallback
import com.androidx.http.net.listener.HttpRequestListener
import com.androidx.http.net.listener.StringCallback
import com.androidx.http.net.module.MsgModule
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*

class HttpRequest : HttpRequestListener {

    private var currentConnect = 0
    private val httpNetwork: IHttpNetwork = HttpNetwork.builder()
    private val handler =
        Handler(Looper.getMainLooper(), Handler.Callback label@{ message: Message ->
            val msg = message.obj as MsgModule
            when (message.what) {
                -2 -> {
                    msg.bytesCallback.onFailure(String(msg.msg1))
                    return@label false
                }
                -1 -> {
                    msg.stringCallback.onFailure(msg.msg)
                    return@label false
                }
                0 -> {
                    msg.stringCallback.onSuccess(msg.msg)
                    return@label false
                }
                1 -> {
                    msg.bytesCallback.onSuccess(msg.msg1)
                    return@label false
                }
                else -> return@label false
            }
        })

    override fun getRequest(
        url: String?,
        map: MutableMap<String, Any>?,
        maxAnewCount: Int,
        callBack: StringCallback?
    ) {
        httpNetwork.client.newCall(httpNetwork.getRequest(url, map)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.sendMessage(
                    handler.obtainMessage(
                        -1, MsgModule(Log.getStackTraceString(e), callBack!!)
                    )
                )
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    httpNetwork.client.newCall(call.request()).enqueue(this)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                handler.sendMessage(
                    handler.obtainMessage(0, response.body?.let {
                        MsgModule(it.string(), callBack!!)
                    }
                    )
                )
            }
        })
    }

    override fun postRequestProto(
        url: String?,
        bytes: ByteArray?,
        maxAnewCount: Int,
        callBack: BytesCallback?
    ) {
        httpNetwork.client.newCall(httpNetwork.postRequestProto(url, bytes))
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    handler.sendMessage(
                        handler.obtainMessage(
                            -2, MsgModule(Log.getStackTraceString(e).toByteArray(), callBack!!)
                        )
                    )
                    // 如果超时并未超过指定次数，则重新连接
                    if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                        currentConnect++
                        httpNetwork.client.newCall(call.request()).enqueue(this)
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    handler.sendMessage(
                        handler.obtainMessage(1, response.body?.let {
                            MsgModule(it.bytes(), callBack!!)
                        }
                        )
                    )
                }
            })
    }

    override fun postRequest(
        url: String?,
        map: MutableMap<String, Any>?,
        maxAnewCount: Int,
        callBack: StringCallback?
    ) {
        httpNetwork.client.newCall(httpNetwork.postRequest(url, map)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.sendMessage(
                    handler.obtainMessage(
                        -1, MsgModule(Log.getStackTraceString(e), callBack!!)
                    )
                )
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    httpNetwork.client.newCall(call.request()).enqueue(this)
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    handler.sendMessage(
                        handler.obtainMessage(0, response.body?.let {
                            MsgModule(it.string(), callBack!!)
                        }
                        )
                    )
                }
            })
    }

    override fun postRequest(
        url: String?,
        json: JsonObject?,
        maxAnewCount: Int,
        callBack: StringCallback?
    ) {
        httpNetwork.client.newCall(httpNetwork.postRequest(url, json)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.sendMessage(
                    handler.obtainMessage(
                        -1, MsgModule(Log.getStackTraceString(e), callBack!!)
                    )
                )
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    httpNetwork.client.newCall(call.request()).enqueue(this)
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    handler.sendMessage(
                        handler.obtainMessage(0, response.body?.let {
                            MsgModule(it.string(), callBack!!)
                        }
                        )
                    )
                }
            })
    }

    override fun deleteRequest(
        url: String?,
        json: JsonObject?,
        maxAnewCount: Int,
        callBack: StringCallback?
    ) {
        httpNetwork.client.newCall(httpNetwork.deleteRequest(url, json)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.sendMessage(
                    handler.obtainMessage(
                        -1, MsgModule(Log.getStackTraceString(e), callBack!!)
                    )
                )
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    httpNetwork.client.newCall(call.request()).enqueue(this)
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    handler.sendMessage(
                        handler.obtainMessage(0,
                            response.body?.let { MsgModule(it.string(), callBack!!) })
                    )
                }
            })
    }

    override fun deleteRequest(
        url: String?,
        map: MutableMap<String, Any>?,
        maxAnewCount: Int,
        callBack: StringCallback?
    ) {
        httpNetwork.client.newCall(httpNetwork.deleteRequest(url, map)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.sendMessage(
                    handler.obtainMessage(
                        -1, MsgModule(Log.getStackTraceString(e), callBack!!)
                    )
                )
                // 如果超时并未超过指定次数，则重新连接
                if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++
                    httpNetwork.client.newCall(call.request()).enqueue(this)
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    handler.sendMessage(
                        handler.obtainMessage(0, response.body?.let {
                            MsgModule(it.string(), callBack!!)
                        }
                        )
                    )
                }
            })
    }

    override fun forrequest(
        url: String?,
        key: String?,
        json: JsonObject?,
        maxAnewCount: Int,
        callBack: StringCallback?
    ) {
        httpNetwork.client.newCall(httpNetwork.forrequest(url, key, json))
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    handler.sendMessage(
                        handler.obtainMessage(
                            -1, MsgModule(Log.getStackTraceString(e), callBack!!)
                        )
                    )
                    // 如果超时并未超过指定次数，则重新连接
                    if (e is SocketTimeoutException && currentConnect < maxAnewCount) {
                        currentConnect++
                        httpNetwork.client.newCall(call.request()).enqueue(this)
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    handler.sendMessage(
                        handler.obtainMessage(0,
                            response.body?.let { MsgModule(it.string(), callBack!!) })
                    )
                }
            })
    }

}