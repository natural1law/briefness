package com.androidx.http.net.listener

sealed interface ActionListener {

    fun online(user: String)

    fun offline(user: String)

}