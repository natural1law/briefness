package com.androidx.http.net.module

import android.os.Parcel
import android.os.Parcelable
import com.androidx.http.base.BaseBean
import com.androidx.http.net.listener.Callback

class MsgModule() : BaseBean() {

    lateinit var msg: String
    lateinit var msg1: ByteArray
    lateinit var callback: Callback

    constructor(parcel: Parcel) : this() {
        msg = parcel.readString()!!
        msg1 = parcel.createByteArray()!!
    }

    constructor(msg: String, callBack: Callback) : this() {
        this.msg = msg
        this.callback = callBack
    }

    constructor(bytes: ByteArray, callBack: Callback) : this() {
        this.msg1 = bytes
        this.callback = callBack
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
        parcel.writeByteArray(msg1)
    }

    companion object CREATOR : Parcelable.Creator<MsgModule> {
        override fun createFromParcel(parcel: Parcel): MsgModule {
            return MsgModule(parcel)
        }

        override fun newArray(size: Int): Array<MsgModule?> {
            return arrayOfNulls(size)
        }
    }

}