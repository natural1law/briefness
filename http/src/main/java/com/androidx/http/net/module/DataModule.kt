package com.androidx.http.net.module

import android.os.Parcel
import android.os.Parcelable
import com.androidx.http.base.BaseBean
import com.androidx.http.net.listener.LoginCallback
import com.androidx.http.net.listener.MsgCallback
import okio.ByteString
import java.io.ObjectInput
import java.io.ObjectOutput

@Suppress("JoinDeclarationAndAssignment")
class DataModule() : BaseBean() {

    lateinit var msg: String
    lateinit var bytes: ByteString
    lateinit var loginCallback: LoginCallback //登录回调
    lateinit var msgCallback: MsgCallback//消息回调

    constructor(parcel: Parcel) : this() {
        parcel.readString()!!
    }

    constructor(failMsg: String, loginCallback: LoginCallback) : this() {
        this.msg = failMsg
        this.loginCallback = loginCallback
    }

    constructor(loginCallback: LoginCallback) : this() {
        this.loginCallback = loginCallback
    }

    constructor(bytes: ByteString, msgCallback: MsgCallback) : this() {
        this.bytes = bytes
        this.msgCallback = msgCallback
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
    }

    override fun writeExternal(out: ObjectOutput?) {
        out!!.writeUTF(msg)
        out.write(bytes.toByteArray())
        out.writeObject(loginCallback)
        out.writeObject(msgCallback)
    }

    override fun readExternal(`in`: ObjectInput?) {
        `in`!!.readUTF()
        `in`.read(bytes.toByteArray())
        `in`.readObject()
        `in`.readObject()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataModule> {
        override fun createFromParcel(parcel: Parcel): DataModule {
            return DataModule(parcel)
        }

        override fun newArray(size: Int): Array<DataModule?> {
            return arrayOfNulls(size)
        }
    }

}
