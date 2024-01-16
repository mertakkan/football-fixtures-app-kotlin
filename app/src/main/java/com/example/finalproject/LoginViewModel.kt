package com.example.finalproject

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.User
import com.example.finalproject.data.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LoginViewModel(private val userRepository: UsersRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var userUiState by mutableStateOf(UserUiState())
        private set

    /**
     * Updates the [indUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(userDetails: UserDetails) {
        userUiState =
            UserUiState(userDetails = userDetails, isEntryValid = validateInput(userDetails))
    }

    private fun validateInput(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && surname.isNotBlank() && username.isNotBlank() && password.isNotBlank()
        }
    }

    suspend fun saveUser() {
        if (validateInput()) {
            userRepository.insertUser(userUiState.userDetails.toUser())
        }
    }

    suspend fun isUserExist(username: String, password: String): Boolean {

        var isExist: Boolean = false

        val userObject: Flow<User?> =
            userRepository.getUserStreamByUsername(username)


        if (userObject.firstOrNull() == null) return false

        val uiState: UserUiState = userObject
            .filterNotNull()
            .map {
                UserUiState(userDetails = it.toUserDetails())
            }
            .first()

        if (uiState.userDetails.username == username &&
            uiState.userDetails.password == password) {
            isExist = true
        }

        return isExist
    }
}

data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
    val isEntryValid: Boolean = false
)

data class UserDetails(
    val id: Int = 0,
    val name: String = "",
    val surname: String = "",
    val username: String = "",
    val password: String = "",
    val profilePicId: String = "",
    val favTeam: String = "",
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun UserDetails.toUser(): User = User(
    id = id,
    name = name,
    surname = surname,
    username = username,
    password = password,
    profilePicId = profilePicId,
    favTeam = favTeam
)

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun User.toUserUiState(isEntryValid: Boolean = false): UserUiState = UserUiState(
    userDetails = this.toUserDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun User.toUserDetails(): UserDetails = UserDetails(
    id = id,
    name = name,
    surname = surname,
    username = username,
    password = password,
    profilePicId = profilePicId,
    favTeam = favTeam
)