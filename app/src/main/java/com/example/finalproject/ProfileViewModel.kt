package com.example.finalproject

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.UsersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UsersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username: String = checkNotNull(savedStateHandle["username"])

    /**
     * Holds the item details ui state. The data is retrieved from [ItemsRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<UserDetailUiState> =
        userRepository.getUserStreamByUsername(username)
            .filterNotNull()
            .map {
                UserDetailUiState(userDetails = it.toUserDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserDetailUiState()
            )
    fun showUiState() {
        println("uiState in profile: ${uiState.value}")
    }

    fun updateInd(name: String, surname: String, profilePicId: String) {
        viewModelScope.launch {
            val currentInd = uiState.value.userDetails.toUser()
            userRepository.updateUser(currentInd.copy(
                name = name,
                surname = surname,
                profilePicId = profilePicId
                )
            )
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for ItemDetailsScreen
 */
data class UserDetailUiState(
    val userDetails: UserDetails = UserDetails()
)