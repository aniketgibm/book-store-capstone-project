package com.bookstore.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookstore.data.local.SessionDataStore
import com.bookstore.domain.model.Order
import com.bookstore.domain.usecase.order.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OrdersUiState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = true,
    val userId: Long = -1L,
    val snackbarMessage: String? = null
)

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val buyAgainUseCase: BuyAgainUseCase,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrdersUiState())
    val uiState: StateFlow<OrdersUiState> = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            val userId = sessionDataStore.currentUserId.first() ?: -1L
            _uiState.update { it.copy(userId = userId, isLoading = false) }
            if (userId > 0) {
                getOrderHistoryUseCase(userId).collect { orders ->
                    _uiState.update { it.copy(orders = orders, isLoading = false) }
                }
            }
        }
    }

    fun cancelOrder(orderId: Long) {
        viewModelScope.launch {
            val result = cancelOrderUseCase(orderId)
            result.fold(
                onSuccess = { _uiState.update { it.copy(snackbarMessage = "Order cancelled successfully") } },
                onFailure = { error -> _uiState.update { it.copy(snackbarMessage = "Cannot cancel: ${error.message}") } }
            )
        }
    }

    fun buyAgain(orderId: Long) {
        viewModelScope.launch {
            val userId = _uiState.value.userId
            val result = buyAgainUseCase(orderId, userId)
            result.fold(
                onSuccess = { _uiState.update { it.copy(snackbarMessage = "Items added to cart!") } },
                onFailure = { err -> _uiState.update { it.copy(snackbarMessage = "Failed to add items: ${err.message}") } }
            )
        }
    }

    fun clearSnackbar() = _uiState.update { it.copy(snackbarMessage = null) }
}
