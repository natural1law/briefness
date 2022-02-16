package com.androidx.http.net

import com.androidx.http.net.listener.DownloadListener
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Request

interface HttpNetworkListener {

    fun getCall(request: Request): Call

    fun getRequest(url: String, map: Map<String, Any>): Call

    fun postRequest(url: String, map: Map<String, Any>): Call

    fun postRequest(url: String, json: JsonObject): Call

    fun deleteRequest(url: String, map: Map<String, Any>): Call

    fun deleteRequest(url: String, json: JsonObject): Call

    fun forrequest(url: String, key: String, json: JsonObject): Call

    fun postRequestProto(url: String, bytes: ByteArray): Call

    fun uploadRequest(url: String, map: Map<String, Any>, key: String, path: String): Call

    fun uploadRequest(url: String, map: Map<String, Any>, key: String, pathList: List<String>): Call

    fun downloadRequest(url: String, downloadListener: DownloadListener): Call

}