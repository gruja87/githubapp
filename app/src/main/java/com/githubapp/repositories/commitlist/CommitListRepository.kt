package com.githubapp.repositories.commitlist

import com.githubapp.data.models.Commit
import com.githubapp.util.Resource
import retrofit2.Response

interface CommitListRepository {
    suspend fun getCommitList(owner: String, repo: String):Resource<List<Commit>>
}