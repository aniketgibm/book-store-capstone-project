package com.bookstore.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookstore.data.local.SessionDataStore
import com.bookstore.domain.model.Book
import com.bookstore.domain.model.Category
import com.bookstore.domain.model.Order
import com.bookstore.domain.usecase.book.*
import com.bookstore.domain.usecase.cart.AddToCartUseCase
import com.bookstore.domain.usecase.order.GetOrderHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val featuredBooks: List<Book> = emptyList(),
    val categories: List<Category> = emptyList(),
    val recommendedBooks: List<Book> = emptyList(),
    val recentOrders: List<Order> = emptyList(),
    val userId: Long = -1L,
    val isGuest: Boolean = false,
    val snackbarMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedBooksUseCase: GetFeaturedBooksUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getRecommendedBooksUseCase: GetRecommendedBooksUseCase,
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val userId = sessionDataStore.currentUserId.first() ?: -1L
            val isGuest = sessionDataStore.isGuest.first()

            _uiState.update { it.copy(userId = userId, isGuest = isGuest) }

            // Load featured books
            launch {
                getFeaturedBooksUseCase().collect { books ->
                    _uiState.update { it.copy(featuredBooks = books, isLoading = false) }
                }
            }

            // Load categories
            launch {
                getCategoriesUseCase().collect { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }
            }

            // Load recommendations and orders if logged in
            if (userId > 0) {
                launch {
                    getRecommendedBooksUseCase(userId).collect { books ->
                        _uiState.update { it.copy(recommendedBooks = books) }
                    }
                }
                launch {
                    getOrderHistoryUseCase(userId).collect { orders ->
                        _uiState.update { it.copy(recentOrders = orders.take(3)) }
                    }
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun addToCart(bookId: Long) {
        viewModelScope.launch {
            val userId = _uiState.value.userId
            if (userId <= 0) {
                _uiState.update { it.copy(snackbarMessage = "Please login to add items to cart") }
                return@launch
            }
            addToCartUseCase(userId, bookId)
            _uiState.update { it.copy(snackbarMessage = "Added to cart!") }
        }
    }

    fun clearSnackbar() = _uiState.update { it.copy(snackbarMessage = null) }
}
