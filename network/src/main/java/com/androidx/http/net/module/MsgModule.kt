package com.androidx.http.net.module

import android.os.Parcel
import android.os.Parcelable
import com.androidx.http.net.listener.Callback
import com.androidx.http.net.listener.Response
import java.io.ObjectInput
import java.io.ObjectOutput

class MsgModule() : BaseBean() {

    lateinit var msg: String
    lateinit var msg1: ByteArray
    lateinit var response: Response
    lateinit var callback: Callback

    constructor(parcel: Parcel) : this() {
        msg = parcel.readString()!!
        msg1 = parcel.createByteArray()!!
    }

    constructor(msg: String, callBack: Response) : this() {
        this.msg = msg
        this.response = callBack
    }

    constructor(bytes: ByteArray, callBack: Callback) : this() {
        this.msg1 = bytes
        this.callback = callBack
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
        parcel.writeByteArray(msg1)
    }

    override fun writeExternal(out: ObjectOutput?) {
        out!!.writeUTF(msg)
        out.write(msg1)
        out.writeObject(response)
        out.writeObject(response)
    }

    override fun readExternal(`in`: ObjectInput?) {
        `in`!!.readUTF()
        `in`.readByte()
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