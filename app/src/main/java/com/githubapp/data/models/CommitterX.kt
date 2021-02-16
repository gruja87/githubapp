package com.githubapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommitterX(
    @SerializedName("avatar_url")
    @Expose
    val avatar_url: String
):Parcelable