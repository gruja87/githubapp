package com.githubapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubapp.data.DispatcherProvider
import com.githubapp.data.models.Profile
import com.githubapp.repositories.profile.ProfileRepository
import com.githubapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    sealed class ProfileEvent {
        class Success(val profile: Profile) : ProfileEvent()
        class Error(val message: String) : ProfileEvent()
        object Loading : ProfileEvent()
        object None : ProfileEvent()
    }

    private val _stateEvent = MutableStateFlow<ProfileEvent>(ProfileEvent.None)
    val stateEvent: StateFlow<ProfileEvent>
        get() = _stateEvent

    fun getProfile() {
        viewModelScope.launch(dispatcherProvider.io) {
            _stateEvent.value = ProfileEvent.Loading
            when (val profileResponse = profileRepository.getProfile()) {
                is Resource.Success -> _stateEvent.value = ProfileEvent.Success(profileResponse.data!!)
                is Resource.Error -> _stateEvent.value = ProfileEvent.Error(profileResponse.message!!)
            }
        }
    }
}
