package com.githubapp.repositories.profile

import com.githubapp.data.DispatcherProvider
import com.githubapp.data.GithubApi
import com.githubapp.data.apiCall
import com.githubapp.data.models.Profile
import com.githubapp.util.Resource
import javax.inject.Inject

class ProfileRepositoryImpl
@Inject
constructor(
    private val githubApi: GithubApi,
    private val dispatcherProvider: DispatcherProvider
) : ProfileRepository {
    override suspend fun getProfile(): Resource<Profile> {
        return apiCall(dispatcherProvider.io) {
            githubApi.getProfile()
        }
    }
}