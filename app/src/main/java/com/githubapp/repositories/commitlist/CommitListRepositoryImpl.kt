package com.githubapp.repositories.commitlist

import com.githubapp.data.DispatcherProvider
import com.githubapp.data.GithubApi
import com.githubapp.data.apiCall
import com.githubapp.data.models.Commit
import com.githubapp.util.Resource
import javax.inject.Inject

class CommitListRepositoryImpl
@Inject
constructor(
    private val githubApi: GithubApi,
    private val dispatcherProvider: DispatcherProvider
) : CommitListRepository {

    override suspend fun getCommitList(owner: String, repo: String): Resource<List<Commit>> {
        return apiCall(dispatcherProvider.io) {
            githubApi.getCommitList(owner, repo)
        }
    }
}
