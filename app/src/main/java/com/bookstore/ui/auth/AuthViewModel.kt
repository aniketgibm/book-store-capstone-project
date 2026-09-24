package com.bookstore.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookstore.data.local.SessionDataStore
import com.bookstore.domain.model.User
import com.bookstore.domain.usecase.auth.LoginUseCase
import com.bookstore.domain.usecase.auth.RegisterUseCase
import com.bookstore.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            val result = loginUseCase(email, password)
            _loginState.value = result.fold(
                onSuccess = { AuthState.Success(it) },
                onFailure = { AuthState.Error(it.message ?: "Login failed") }
            )
        }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            val result = registerUseCase(name, email, password, confirmPassword)
            _registerState.value = result.fold(
                onSuccess = { AuthState.Success(it) },
                onFailure = { AuthState.Error(it.message ?: "Registration failed") }
            )
        }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            sessionDataStore.setGuestMode()
        }
    }

    fun resetLoginState() { _loginState.value = AuthState.Idle }
    fun resetRegisterState() { _registerState.value = AuthState.Idle }
}
