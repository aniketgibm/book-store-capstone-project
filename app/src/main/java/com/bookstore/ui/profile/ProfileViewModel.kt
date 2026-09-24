package com.bookstore.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookstore.data.local.SessionDataStore
import com.bookstore.domain.model.Address
import com.bookstore.domain.model.User
import com.bookstore.domain.usecase.auth.LogoutUseCase
import com.bookstore.domain.usecase.user.GetAddressesUseCase
import com.bookstore.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val user: User? = null,
    val addresses: List<Address> = emptyList(),
    val isLoading: Boolean = true,
    val isLoggedOut: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getAddressesUseCase: GetAddressesUseCase,
    private val userRepository: UserRepository,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val userId = sessionDataStore.currentUserId.first() ?: -1L
            if (userId <= 0) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }
            val user = userRepository.getUserById(userId)
            _uiState.update { it.copy(user = user, isLoading = false) }

            getAddressesUseCase(userId).collect { addresses ->
                _uiState.update { it.copy(addresses = addresses) }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.update { it.copy(isLoggedOut = true) }
        }
    }
}
