package com.androidx.http.net.listener

import com.google.protobuf.ByteString

sealed interface MsgCallback {
    fun message(code: Int, msg: String, data: ByteString)
}