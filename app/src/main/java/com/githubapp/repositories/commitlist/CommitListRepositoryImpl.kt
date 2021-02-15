package com.githubapp.repositories.commitlist

import com.githubapp.data.GithubApi
import com.githubapp.data.apiCall
import com.githubapp.data.models.Commit
import com.githubapp.util.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CommitListRepositoryImpl
@Inject
constructor(
    private val githubApi: GithubApi
) : CommitListRepository {

    override suspend fun getCommitList(owner: String, repo: String): Resource<List<Commit>> {
        return apiCall(Dispatchers.IO) {
            githubApi.getCommitList(owner, repo)
        }
    }
}
