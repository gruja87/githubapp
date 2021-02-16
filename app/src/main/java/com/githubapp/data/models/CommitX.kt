package com.githubapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommitX(
    @SerializedName("comment_count")
    @Expose
    val comment_count: Int,
    @SerializedName("committer")
    @Expose
    val committer: Committer? = null,
    @SerializedName("message")
    @Expose
    val message: String
) : Parcelable