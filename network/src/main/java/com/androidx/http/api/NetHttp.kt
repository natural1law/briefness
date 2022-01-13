@file:Suppress("unused")

package com.androidx.http.api

import com.androidx.http.net.HttpRequest
import com.androidx.http.net.listener.Callback
import com.androidx.http.net.listener.DownloadListener
import com.androidx.http.net.listener.HttpRequestListener
import com.androidx.http.net.listener.Response
import com.google.gson.JsonObject
import java.util.concurrent.ConcurrentHashMap

class NetHttp private constructor() {

    companion object {

        const val GET_MAP = 1
        const val POST_JSON = 2
        const val POST_MAP = 3
        const val POST_BYTES = 4
        const val DEL_MAP = 5
        const val DEL_JSON = 6
        const val FROM_JSON = 7
        const val UPLOAD = 8
        const val DOWNLOAD = 9

        @JvmStatic
        fun builder(): Builder {
            synchronized(Builder::class.java) { return Singleton.getInstance()!! }
        }

    }

    class Builder internal constructor() {

        private val builder: Builder = this
        internal var maxAnewCount: Int? = 3
        internal var host: String? = ""
        internal var mode: Int? = 0
        internal var jsonKey: String? = ""
        internal var map: Map<String, Any> = ConcurrentHashMap()
        internal var json = JsonObject()
        internal var bytes: ByteArray? = null
        internal var response: Response? = null
        internal var listener: DownloadListener? = null
        internal var callback: Callback? = null
        internal var filePath: String? = null

        fun setMaxAnewCount(maxAnewCount: Int?): Builder {
            this.maxAnewCount = maxAnewCount
            return builder
        }

        fun setHosts(url: String): Builder {
            this.host = url
            return builder
        }

        fun setMode(mode: Int?): Builder {
            this.mode = mode
            return builder
        }

        fun setMap(map: Map<String, Any>): Builder {
            this.map = map
            return builder
        }

        fun setJson(json: JsonObject): Builder {
            this.json = json
            return builder
        }

        fun setJsonKey(key: String): Builder {
            this.jsonKey = key
            return builder
        }

        fun setBytes(bytes: ByteArray): Builder {
            this.bytes = bytes
            return builder
        }

        fun setCallback(response: Response?): Builder {
            this.response = response
            return builder
        }

        fun setCallback(callback: Callback?): Builder {
            this.callback = callback
            return builder
        }

        fun setListener(listener: DownloadListener?): Builder {
            this.listener = listener
            return builder
        }

        fun setFile(path: String?): Builder {
            this.filePath = path
            return builder
        }

        fun build(): NetHttp {
            return NetHttp(builder)
        }

    }

    private fun init(builder: Builder, requestListener: HttpRequestListener) {
        when (builder.mode) {
            GET_MAP -> requestListener.getRequest(
                builder.host,
                builder.map,
                builder.maxAnewCount!!,
                builder.response
            )
            POST_JSON -> requestListener.postRequest(
                builder.host,
                builder.json,
                builder.maxAnewCount!!,
                builder.response
            )
            POST_MAP -> requestListener.postRequest(
                builder.host,
                builder.map,
                builder.maxAnewCount!!,
                builder.response
            )
            POST_BYTES -> requestListener.postRequestProto(
                builder.host,
                builder.bytes,
                builder.maxAnewCount!!,
                builder.callback
            )
            DEL_MAP -> requestListener.deleteRequest(
                builder.host,
                builder.map,
                builder.maxAnewCount!!,
                builder.response
            )
            FROM_JSON -> requestListener.forrequest(
                builder.host,
                builder.jsonKey,
                builder.json,
                builder.maxAnewCount!!,
                builder.response
            )
            DEL_JSON -> requestListener.deleteRequest(
                builder.host,
                builder.json,
                builder.maxAnewCount!!,
                builder.response
            )
            UPLOAD -> requestListener.upload(
                builder.host,
                builder.jsonKey,
                builder.filePath,
                builder.maxAnewCount!!,
                builder.response,
            )
            DOWNLOAD -> requestListener.download(
                builder.host,
                builder.filePath,
                builder.maxAnewCount!!,
                builder.listener
            )
        }
    }

    private constructor(b: Builder) : this() {
        init(b, HttpRequest())
    }

    private object Singleton {
        @Volatile
        private var instance: Builder? = null

        @JvmStatic
        private val INSTANCE = Builder()

        fun getInstance(): Builder? {
            if (instance == null) synchronized(Builder::class.java) {
                if (instance == null) return INSTANCE.also { instance = it }
            }
            return instance
        }
    }

}