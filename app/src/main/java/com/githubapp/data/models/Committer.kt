package com.githubapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Committer(
    @SerializedName("date")
    @Expose
    val date: String? = null,
    val email: String,
    @SerializedName("name")
    @Expose
    val name: String
)