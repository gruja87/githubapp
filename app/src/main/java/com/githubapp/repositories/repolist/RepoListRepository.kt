package com.githubapp.repositories.repolist

import com.githubapp.data.models.Repo
import com.githubapp.util.Resource

interface RepoListRepository {
    suspend fun getReposList(): Resource<List<Repo>>
}
