package com.androidx.http.net.listener

sealed interface ResponseType<C> {

    fun onSuccess(data: C)

    fun onFailure(msg: String) {

    }

}