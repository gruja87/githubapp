package com.githubapp.repositories.repolist

import com.githubapp.data.DispatcherProvider
import com.githubapp.data.GithubApi
import com.githubapp.data.apiCall
import com.githubapp.data.models.Repo
import com.githubapp.util.Resource
import javax.inject.Inject

class RepoListRepositoryImpl
@Inject
constructor(
    private val githubApi: GithubApi,
    private val dispatcherProvider: DispatcherProvider
) : RepoListRepository {

    override suspend fun getReposList(): Resource<List<Repo>> {
        return apiCall(dispatcherProvider.io) {
            githubApi.getRepoList()
        }
    }
}