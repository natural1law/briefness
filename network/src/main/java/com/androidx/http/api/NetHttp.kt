@file:Suppress("unused")

package com.androidx.http.api

import android.net.Uri
import com.androidx.http.net.HttpRequest
import com.androidx.http.net.listener.BytesCallback
import com.androidx.http.net.listener.HttpRequestListener
import com.androidx.http.net.listener.StringCallback
import com.androidx.reduce.tools.Secure
import com.google.gson.JsonObject
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

        private val builder: Builder = this
        internal var maxAnewCount: Int? = 3
        internal var host: String? = null
        internal var mode: Int? = 0
        internal var jsonKey: String? = ""
        internal var key: String? = ""
        internal var map: Map<String, Any> = ConcurrentHashMap()
        internal var json = JsonObject()
        internal var bytes: ByteArray? = null
        internal var stringCallback: StringCallback? = null
        internal var bytesCallback: BytesCallback? = null

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

        fun setKey(key: String): Builder {
            this.key = key
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

        fun setCallback(stringCallback: StringCallback?): Builder {
            this.stringCallback = stringCallback
            return builder
        }

        fun setCallback(bytesCallback: BytesCallback?): Builder {
            this.bytesCallback = bytesCallback
            return builder
        }

        fun build(): NetHttp {
            return NetHttp(builder)
        }

    }

    private fun init(builder: Builder, urlEncoder: String?, requestListener: HttpRequestListener) {
        val url = if (builder.key.isNullOrEmpty()) Uri.parse(Uri.encode(urlEncoder))
        else Uri.parse(Uri.encode(Secure.AES.decrypt(builder.key, urlEncoder)))
        when (builder.mode) {
            GET_MAP -> requestListener.getRequest(
                url,
                builder.map,
                builder.maxAnewCount!!,
                builder.stringCallback
            )
            POST_JSON -> requestListener.postRequest(
                url,
                builder.json,
                builder.maxAnewCount!!,
                builder.stringCallback
            )
            POST_MAP -> requestListener.postRequest(
                url,
                builder.map,
                builder.maxAnewCount!!,
                builder.stringCallback
            )
            POST_BYTES -> requestListener.postRequestProto(
                url,
                builder.bytes,
                builder.maxAnewCount!!,
                builder.bytesCallback
            )
            DEL_MAP -> requestListener.deleteRequest(
                url,
                builder.map,
                builder.maxAnewCount!!,
                builder.stringCallback
            )
            FROM_JSON -> requestListener.forrequest(
                url,
                builder.jsonKey,
                builder.json,
                builder.maxAnewCount!!,
                builder.stringCallback
            )
            DEL_JSON -> requestListener.deleteRequest(
                url,
                builder.json,
                builder.maxAnewCount!!,
                builder.stringCallback
            )
        }
    }

    private constructor(b: Builder) : this() {
        init(b, b.host, HttpRequest())
    }

    private object SingletonHolder {
        val holder = Builder()
    }

}