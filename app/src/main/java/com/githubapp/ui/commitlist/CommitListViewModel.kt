package com.githubapp.ui.commitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubapp.data.models.Commit
import com.githubapp.repositories.commitlist.CommitListRepository
import com.githubapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommitListViewModel
@Inject
constructor(
    private val commitsListRepository: CommitListRepository
) : ViewModel() {

    sealed class CommitsEvent {
        class Success(val commitsList: List<Commit>) : CommitsEvent()
        class Error(val message: String) : CommitsEvent()
        object Loading : CommitsEvent()
        object None : CommitsEvent()
    }

    private val _stateEvent = MutableStateFlow<CommitsEvent>(CommitsEvent.None)
    val stateEvent: StateFlow<CommitsEvent>
        get() = _stateEvent

    fun getCommitList(owner: String, repo: String) {
        viewModelScope.launch {
            _stateEvent.value = CommitsEvent.Loading
            when (val commitListResponse = commitsListRepository.getCommitList(owner, repo)) {
                is Resource.Success -> _stateEvent.value =
                    CommitsEvent.Success(commitListResponse.data!!)
                is Resource.Error -> _stateEvent.value =
                    CommitsEvent.Error(commitListResponse.message!!)
            }
        }
    }
}
