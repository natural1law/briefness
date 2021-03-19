package com.androidx.http.net.module

import android.os.Parcel
import android.os.Parcelable
import com.androidx.http.base.BaseBean
import com.androidx.http.net.listener.BytesCallback
import com.androidx.http.net.listener.StringCallback
import java.io.ObjectInput
import java.io.ObjectOutput

class MsgModule() : BaseBean() {

    lateinit var msg: String
    lateinit var msg1: ByteArray
    lateinit var stringCallback: StringCallback
    lateinit var bytesCallback: BytesCallback

    constructor(parcel: Parcel) : this() {
        msg = parcel.readString()!!
        msg1 = parcel.createByteArray()!!
    }

    constructor(msg: String, callBack: StringCallback) : this() {
        this.msg = msg
        this.stringCallback = callBack
    }

    constructor(bytes: ByteArray, callBack: BytesCallback) : this() {
        this.msg1 = bytes
        this.bytesCallback = callBack
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
        parcel.writeByteArray(msg1)
    }

    override fun writeExternal(out: ObjectOutput?) {
        out!!.writeUTF(msg)
        out.write(msg1)
        out.writeObject(stringCallback)
        out.writeObject(stringCallback)
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