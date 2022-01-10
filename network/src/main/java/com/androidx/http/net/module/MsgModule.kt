package com.androidx.http.net.module

import android.os.Parcel
import android.os.Parcelable
import com.androidx.http.net.listener.Callback
import com.androidx.http.net.listener.DownloadListener
import com.androidx.http.net.listener.Response
import java.io.File
import java.io.ObjectInput
import java.io.ObjectOutput

class MsgModule() : BaseBean() {

    lateinit var msg: String
    lateinit var msg1: ByteArray
    lateinit var response: Response
    lateinit var callback: Callback

    lateinit var downloadListener: DownloadListener
    var msg2: File? = null
    var msg3: Double = 0.0

    constructor(parcel: Parcel) : this() {
        msg = parcel.readString()!!
        msg1 = parcel.createByteArray()!!
        msg3 = parcel.readDouble()
    }

    constructor(msg: String, callBack: Response) : this() {
        this.msg = msg
        this.response = callBack
    }

    constructor(bytes: ByteArray, callBack: Callback) : this() {
        this.msg1 = bytes
        this.callback = callBack
    }

    constructor(file: File, duration: Double, downloadListener: DownloadListener) : this() {
        this.msg2 = file
        this.msg3 = duration
        this.downloadListener = downloadListener
    }

    constructor(msg: String, downloadListener: DownloadListener) : this() {
        this.msg = msg
        this.downloadListener = downloadListener
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
        parcel.writeByteArray(msg1)
    }

    override fun writeExternal(out: ObjectOutput?) {
        out!!.writeUTF(msg)
        out.write(msg1)
        out.writeObject(msg2)
        out.writeDouble(msg3)
        out.writeObject(response)
        out.writeObject(callback)
        out.writeObject(downloadListener)
    }

    override fun readExternal(`in`: ObjectInput?) {
        `in`!!.readUTF()
        `in`.readByte()
        `in`.readObject()
        `in`.readDouble()
        `in`.readObject()
        `in`.readObject()
        `in`.readObject()
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