package com.githubapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("avatar_url")
    @Expose
    val avatar_url: String,
    @SerializedName("company")
    @Expose
    val company: String,
    @SerializedName("name")
    @Expose
    val name: String,
)