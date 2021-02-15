package com.githubapp.ui.repolist.state

sealed class RepoListStateEvent {
    object GetReposListEvent : RepoListStateEvent()
}
