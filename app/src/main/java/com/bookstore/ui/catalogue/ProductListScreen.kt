package com.bookstore.ui.catalogue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.ui.components.BookCard
import com.bookstore.ui.components.EmptyState
import com.bookstore.ui.components.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    categoryId: Long,
    categoryName: String,
    onNavigateToProductDetail: (Long) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: CatalogueViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(categoryId) {
        viewModel.loadBooksForCategory(categoryId)
    }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    val brands = uiState.books.map { it.brand }.distinct().sorted()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(categoryName) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Brand filter chips
            if (brands.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            onClick = { viewModel.filterByBrand("") },
                            label = { Text("All") },
                            selected = uiState.selectedBrand.isEmpty()
                        )
                    }
                    items(brands) { brand ->
                        FilterChip(
                            onClick = { viewModel.filterByBrand(brand) },
                            label = { Text(brand) },
                            selected = uiState.selectedBrand == brand
                        )
                    }
                }
            }

            when {
                uiState.isLoading -> LoadingScreen()
                uiState.filteredBooks.isEmpty() -> EmptyState(
                    title = "No Books Found",
                    message = "Try selecting a different brand or category"
                )
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.filteredBooks) { book ->
                            BookCard(
                                book = book,
                                onClick = { onNavigateToProductDetail(book.id) },
                                onAddToCart = { viewModel.addToCart(book.id) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
