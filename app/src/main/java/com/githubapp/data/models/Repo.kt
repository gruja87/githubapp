package com.githubapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("full_name")
    @Expose
    val full_name: String,
    @SerializedName("has_issues")
    @Expose
    val has_issues: Boolean,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("open_issues_count")
    @Expose
    val open_issues_count: Int,
    @SerializedName("owner")
    @Expose
    val owner: Owner,
)