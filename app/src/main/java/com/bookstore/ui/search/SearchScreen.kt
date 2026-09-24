package com.bookstore.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.ui.catalogue.CatalogueViewModel
import com.bookstore.ui.components.BookCard
import com.bookstore.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToProductDetail: (Long) -> Unit,
    viewModel: CatalogueViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = query,
                        onValueChange = {
                            query = it
                            viewModel.search(it)
                        },
                        placeholder = { Text("Search books, authors...") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        when {
            query.isEmpty() -> EmptyState(
                title = "Search Books",
                message = "Type to search by title or author",
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            )
            uiState.filteredBooks.isEmpty() -> EmptyState(
                title = "No results",
                message = "No books found for \"$query\"",
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            )
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
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
