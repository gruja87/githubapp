package com.githubapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Commit(
    val author: Author,
    val comments_url: String,
    @SerializedName("commit")
    @Expose
    val commit: CommitX? = null,
    @SerializedName("committer")
    @Expose
    val committer: CommitterX? = null
)