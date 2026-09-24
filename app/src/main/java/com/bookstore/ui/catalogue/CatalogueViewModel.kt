package com.bookstore.ui.catalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookstore.data.local.SessionDataStore
import com.bookstore.domain.model.Book
import com.bookstore.domain.model.Category
import com.bookstore.domain.usecase.book.*
import com.bookstore.domain.usecase.cart.AddToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CatalogueUiState(
    val categories: List<Category> = emptyList(),
    val books: List<Book> = emptyList(),
    val filteredBooks: List<Book> = emptyList(),
    val selectedCategoryId: Long = -1L,
    val selectedBrand: String = "",
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val relatedBooks: List<Book> = emptyList(),
    val selectedBook: Book? = null,
    val snackbarMessage: String? = null
)

@HiltViewModel
class CatalogueViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getBooksByCategoryUseCase: GetBooksByCategoryUseCase,
    private val getBookDetailUseCase: GetBookDetailUseCase,
    private val getRelatedBooksUseCase: GetRelatedBooksUseCase,
    private val searchBooksUseCase: SearchBooksUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogueUiState())
    val uiState: StateFlow<CatalogueUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    fun loadBooksForCategory(categoryId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, selectedCategoryId = categoryId, selectedBrand = "") }
            getBooksByCategoryUseCase(categoryId).collect { books ->
                _uiState.update { it.copy(books = books, filteredBooks = books, isLoading = false) }
            }
        }
    }

    fun filterByBrand(brand: String) {
        val books = _uiState.value.books
        val filtered = if (brand.isEmpty()) books else books.filter { it.brand == brand }
        _uiState.update { it.copy(selectedBrand = brand, filteredBooks = filtered) }
    }

    fun loadBookDetail(bookId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val book = getBookDetailUseCase(bookId)
            _uiState.update { it.copy(selectedBook = book, isLoading = false) }
            if (book != null) {
                loadRelatedBooks(bookId, book.categoryId)
            }
        }
    }

    private fun loadRelatedBooks(bookId: Long, categoryId: Long) {
        viewModelScope.launch {
            getRelatedBooksUseCase(bookId, categoryId).collect { related ->
                _uiState.update { it.copy(relatedBooks = related) }
            }
        }
    }

    fun search(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.length >= 2) {
            viewModelScope.launch {
                searchBooksUseCase(query).collect { books ->
                    _uiState.update { it.copy(filteredBooks = books) }
                }
            }
        } else if (query.isEmpty()) {
            _uiState.update { it.copy(filteredBooks = _uiState.value.books) }
        }
    }

    fun addToCart(bookId: Long, quantity: Int = 1) {
        viewModelScope.launch {
            val userId = sessionDataStore.currentUserId.first() ?: -1L
            if (userId <= 0) {
                _uiState.update { it.copy(snackbarMessage = "Please login to add items to cart") }
                return@launch
            }
            addToCartUseCase(userId, bookId, quantity)
            _uiState.update { it.copy(snackbarMessage = "Added to cart!") }
        }
    }

    fun clearSnackbar() = _uiState.update { it.copy(snackbarMessage = null) }
}
