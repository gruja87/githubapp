package com.githubapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Commit(
    val author: Author,
    val comments_url: String,
    @SerializedName("commit")
    @Expose
    val commit: CommitX,
    val committer: CommitterX,
    val html_url: String,
    val node_id: String,
    val parents: List<Parent>,
    val sha: String,
    val url: String
)