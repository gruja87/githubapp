package com.githubapp.repositories.profile

import com.githubapp.data.GithubApi
import com.githubapp.data.apiCall
import com.githubapp.data.models.Profile
import com.githubapp.util.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ProfileRepositoryImpl
@Inject
constructor(
    private val githubApi: GithubApi
) : ProfileRepository {
    override suspend fun getProfile(): Resource<Profile> {
        return apiCall(Dispatchers.IO) {
            githubApi.getProfile()
        }
    }
}