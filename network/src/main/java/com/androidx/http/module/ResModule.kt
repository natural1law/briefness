package com.androidx.http.module

import android.os.Parcel
import android.os.Parcelable
import com.androidx.http.base.BaseBean
import java.io.ObjectInput
import java.io.ObjectOutput

data class ResModule(
    val msg: String?,
    val user: String?
) : BaseBean() {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
        parcel.writeString(user)
    }

    override fun writeExternal(out: ObjectOutput?) {
        out!!.writeUTF(msg)
        out.writeUTF(user)
    }

    override fun readExternal(`in`: ObjectInput?) {
        `in`!!.readUTF()
        `in`.readUTF()
    }

    companion object CREATOR : Parcelable.Creator<ResModule> {
        override fun createFromParcel(parcel: Parcel): ResModule {
            return ResModule(parcel)
        }

        override fun newArray(size: Int): Array<ResModule?> {
            return arrayOfNulls(size)
        }
    }

}
