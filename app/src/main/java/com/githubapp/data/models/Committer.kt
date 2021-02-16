package com.githubapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Committer(
    @SerializedName("date")
    @Expose
    val date: String? = null,
    val email: String,
    @SerializedName("name")
    @Expose
    val name: String
):Parcelable