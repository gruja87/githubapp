package com.githubapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Commit(
    @SerializedName("sha")
    @Expose
    val sha: String,
    @SerializedName("commit")
    @Expose
    val commit: CommitX? = null,
    @SerializedName("committer")
    @Expose
    val committer: CommitterX? = null,
):Parcelable