package com.githubapp.data

import com.githubapp.data.models.Commit
import com.githubapp.data.models.Profile
import com.githubapp.data.models.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/octocat/repos")
    suspend fun getRepoList(): List<Repo>

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommitList(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<Commit>

    @GET("users/octocat")
    suspend fun getProfile(): Profile
}
