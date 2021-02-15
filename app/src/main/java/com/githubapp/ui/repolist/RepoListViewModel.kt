package com.githubapp.ui.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubapp.data.models.Repo
import com.githubapp.repositories.repolist.RepoListRepository
import com.githubapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel
@Inject
constructor(
    private val reposListRepository: RepoListRepository
) : ViewModel() {

    sealed class ReposEvent {
        class Success(val reposList: List<Repo>) : ReposEvent()
        class Error(val message: String) : ReposEvent()
        object Loading : ReposEvent()
        object None : ReposEvent()
    }

    private val _stateEvent = MutableStateFlow<ReposEvent>(ReposEvent.None)
    val stateEvent: StateFlow<ReposEvent>
        get() = _stateEvent

    fun getReposList() {
        viewModelScope.launch(Dispatchers.IO) {
            _stateEvent.value = ReposEvent.Loading
            when (val reposListResponse = reposListRepository.getReposList()) {
                is Resource.Success -> _stateEvent.value = ReposEvent.Success(reposListResponse.data as List<Repo>)
                is Resource.Error -> _stateEvent.value = ReposEvent.Error(reposListResponse.message!!)
            }
        }
    }
}
