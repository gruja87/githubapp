package com.githubapp.repositories.profile

import com.githubapp.data.models.Profile
import com.githubapp.util.Resource

interface ProfileRepository {
    suspend fun getProfile(): Resource<Profile>
}
