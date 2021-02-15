package com.githubapp.repositories.repolist

import com.githubapp.data.GithubApi
import com.githubapp.data.apiCall
import com.githubapp.data.models.Repo
import com.githubapp.util.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RepoListRepositoryImpl
@Inject
constructor(
    private val githubApi: GithubApi
) : RepoListRepository {

    override suspend fun getReposList(): Resource<List<Repo>> {
//        return try {
//            val response = githubApi.getReposList()
//            val result = response.body()
//            if (response.isSuccessful && !result.isNullOrEmpty()) {
//                Resource.Success(result)
//            } else {
//                Resource.Error(response.message())
//            }
//        } catch (ex: Exception) {
//            Resource.Error(ex.message ?: "An error occurred")
//        }

        return apiCall(Dispatchers.IO) {
            githubApi.getRepoList()
        }
    }
}