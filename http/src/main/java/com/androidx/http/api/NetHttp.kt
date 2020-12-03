@file:Suppress("unused")

package com.androidx.http.api

import com.androidx.http.net.HttpRequest
import com.androidx.http.net.listener.Callback
import com.androidx.http.net.listener.HttpRequestListener
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap

class NetHttp private constructor() {

    companion object {
        private val instance = SingletonHolder.holder
        const val GET_MAP = 1
        const val POST_JSON = 2
        const val POST_MAP = 3
        const val POST_BYTES = 4
        const val DEL_MAP = 5
        const val DEL_JSON = 6
        const val FROM_JSON = 7

        fun builder(): Builder {
            synchronized(Builder::class.java) { return instance }
        }
    }

    class Builder internal constructor() {

        init {
            System.loadLibrary("message")
        }

        private external fun host(host: String, port: String): String?
        private external fun hosts(host: String): String?

        private val builder: Builder = this
        internal var maxAnewCount: Int? = 3
        internal var urlSuffix: String? = null
        internal var host: String? = null
        internal var mode: Int? = 0
        internal var map: Map<String, Any> = ConcurrentHashMap()
        internal var json = JSONObject()
        internal var bytes: ByteArray? = null
        internal var callback: Callback? = null


        fun setMaxAnewCount(maxAnewCount: Int?): Builder {
            this.maxAnewCount = maxAnewCount
            return builder
        }

        fun setHost(host: String?, port: String?, isHttps: Boolean): Builder {
            if (isHttps) {
                this.host = host?.let { port?.let { hosts(it) } }
            } else {
                this.host = host?.let { port?.let { it1 -> host(it, it1) } }
            }
            return builder
        }

        fun setUrlSuffix(urlSuffix: String?): Builder {
            this.urlSuffix = urlSuffix
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

        fun setJson(json: JSONObject): Builder {
            this.json = json
            return builder
        }

        fun setBytes(bytes: ByteArray): Builder {
            this.bytes = bytes
            return builder
        }

        fun setCallback(callback: Callback?): Builder {
            this.callback = callback
            return builder
        }

        fun build(): NetHttp {
            return NetHttp(builder)
        }

    }

    private fun init(builder: Builder, url: String?, requestListener: HttpRequestListener) {
        when (builder.mode) {
            GET_MAP -> requestListener.getRequest(
                    url,
                    builder.map,
                    builder.maxAnewCount!!,
                    builder.callback
            )
            POST_JSON -> requestListener.postRequest(
                    url,
                    builder.json,
                    builder.maxAnewCount!!,
                    builder.callback
            )
            POST_MAP -> requestListener.postRequest(
                    url,
                    builder.map,
                    builder.maxAnewCount!!,
                    builder.callback
            )
            POST_BYTES -> requestListener.postRequestProto(
                    url,
                    builder.bytes,
                    builder.maxAnewCount!!,
                    builder.callback
            )
            DEL_MAP -> requestListener.deleteRequest(
                    url,
                    builder.map,
                    builder.maxAnewCount!!,
                    builder.callback
            )
            FROM_JSON -> requestListener.formRequest(
                    url,
                    builder.json,
                    builder.maxAnewCount!!,
                    builder.callback
            )
            DEL_JSON -> requestListener.deleteRequest(
                    url,
                    builder.json,
                    builder.maxAnewCount!!,
                    builder.callback
            )
        }
    }

    private constructor(b: Builder) : this() {
        val url = b.host + b.urlSuffix
        init(b, url, HttpRequest())
    }

    private object SingletonHolder {
        val holder = Builder()
    }

}