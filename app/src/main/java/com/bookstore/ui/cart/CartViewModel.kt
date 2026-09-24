package com.bookstore.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookstore.data.local.SessionDataStore
import com.bookstore.domain.model.Book
import com.bookstore.domain.model.CartItem
import com.bookstore.domain.usecase.book.GetRecommendedBooksUseCase
import com.bookstore.domain.usecase.cart.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val recommendations: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val userId: Long = -1L,
    val isGuest: Boolean = false,
    val subtotal: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val total: Double = 0.0,
    val snackbarMessage: String? = null
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val getRecommendedBooksUseCase: GetRecommendedBooksUseCase,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            val userId = sessionDataStore.currentUserId.first() ?: -1L
            val isGuest = sessionDataStore.isGuest.first()
            _uiState.update { it.copy(userId = userId, isGuest = isGuest, isLoading = false) }

            if (userId > 0) {
                launch {
                    getCartUseCase(userId).collect { items ->
                        val subtotal = items.sumOf { it.book.price * it.quantity }
                        val deliveryFee = if (subtotal > 500) 0.0 else if (items.isNotEmpty()) 49.0 else 0.0
                        _uiState.update {
                            it.copy(
                                cartItems = items,
                                subtotal = subtotal,
                                deliveryFee = deliveryFee,
                                total = subtotal + deliveryFee
                            )
                        }
                    }
                }
                launch {
                    getRecommendedBooksUseCase(userId).collect { books ->
                        _uiState.update { it.copy(recommendations = books.take(5)) }
                    }
                }
            }
        }
    }

    fun removeItem(cartItemId: Long) {
        viewModelScope.launch {
            removeFromCartUseCase(cartItemId)
        }
    }

    fun updateQuantity(cartItemId: Long, quantity: Int) {
        viewModelScope.launch {
            updateCartQuantityUseCase(cartItemId, quantity)
        }
    }

    fun addToCart(bookId: Long) {
        viewModelScope.launch {
            val userId = _uiState.value.userId
            if (userId <= 0) return@launch
            addToCartUseCase(userId, bookId)
            _uiState.update { it.copy(snackbarMessage = "Added to cart!") }
        }
    }

    fun clearSnackbar() = _uiState.update { it.copy(snackbarMessage = null) }
}
