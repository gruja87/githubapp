package com.githubapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommitX(
    @SerializedName("author")
    @Expose
    val author: AuthorX,
    val comment_count: Int,
    @SerializedName("committer")
    @Expose
    val committer: Committer,
    @SerializedName("message")
    @Expose
    val message: String,
    val tree: Tree,
    val url: String,
    val verification: Verification
)