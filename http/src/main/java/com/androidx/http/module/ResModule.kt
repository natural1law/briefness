package com.androidx.http.module

import android.os.Parcel
import android.os.Parcelable
import com.androidx.http.base.BaseBean

data class ResModule(
    val msg: String,
    val user: String
) : BaseBean() {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
        parcel.writeString(user)
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
