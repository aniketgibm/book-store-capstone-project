package com.bookstore.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.domain.model.Book
import com.bookstore.domain.model.Category
import com.bookstore.domain.model.Order
import com.bookstore.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProductDetail: (Long) -> Unit,
    onNavigateToCatalogue: () -> Unit,
    onNavigateToCategory: (Long, String) -> Unit,
    onOpenDrawer: () -> Unit,
    onNavigateToSearch: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

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
                    Column {
                        Text("BookStore", style = MaterialTheme.typography.titleLarge)
                        if (!uiState.isGuest && uiState.userId > 0) {
                            Text("Welcome back!", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingScreen(modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Featured Books Banner
                if (uiState.featuredBooks.isNotEmpty()) {
                    item {
                        SectionHeader(
                            title = "Featured Books",
                            actionLabel = "See All",
                            onAction = onNavigateToCatalogue
                        )
                        FeaturedBooksRow(
                            books = uiState.featuredBooks,
                            onBookClick = onNavigateToProductDetail,
                            onAddToCart = { viewModel.addToCart(it) }
                        )
                    }
                }

                // Categories
                if (uiState.categories.isNotEmpty()) {
                    item {
                        SectionHeader(title = "Browse Categories")
                        CategoryChipRow(
                            categories = uiState.categories,
                            onCategoryClick = onNavigateToCategory
                        )
                    }
                }

                // Recommendations
                if (uiState.recommendedBooks.isNotEmpty()) {
                    item {
                        SectionHeader(title = "Recommended for You")
                        BooksHorizontalRow(
                            books = uiState.recommendedBooks,
                            onBookClick = onNavigateToProductDetail,
                            onAddToCart = { viewModel.addToCart(it) }
                        )
                    }
                } else if (!uiState.isGuest && uiState.userId > 0 && uiState.recentOrders.isEmpty()) {
                    item {
                        SectionHeader(title = "Recommended for You")
                        BooksHorizontalRow(
                            books = uiState.featuredBooks.take(5),
                            onBookClick = onNavigateToProductDetail,
                            onAddToCart = { viewModel.addToCart(it) }
                        )
                    }
                } else if (uiState.isGuest) {
                    item {
                        GuestRecommendationsBanner(onLogin = {})
                    }
                }

                // Buy Again
                if (uiState.recentOrders.isNotEmpty()) {
                    item {
                        SectionHeader(
                            title = "Buy Again",
                            actionLabel = "View All Orders",
                            onAction = {}
                        )
                        BuyAgainRow(
                            orders = uiState.recentOrders,
                            onBookClick = onNavigateToProductDetail,
                            onAddToCart = { viewModel.addToCart(it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedBooksRow(
    books: List<Book>,
    onBookClick: (Long) -> Unit,
    onAddToCart: (Long) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { book ->
            BookCard(
                book = book,
                onClick = { onBookClick(book.id) },
                onAddToCart = { onAddToCart(book.id) },
                modifier = Modifier.width(160.dp)
            )
        }
    }
}

@Composable
fun BooksHorizontalRow(
    books: List<Book>,
    onBookClick: (Long) -> Unit,
    onAddToCart: (Long) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { book ->
            BookCard(
                book = book,
                onClick = { onBookClick(book.id) },
                onAddToCart = { onAddToCart(book.id) }
            )
        }
    }
}

@Composable
fun CategoryChipRow(
    categories: List<Category>,
    onCategoryClick: (Long, String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                onClick = { onCategoryClick(category.id, category.name) },
                label = { Text("${category.iconEmoji} ${category.name}") },
                selected = false
            )
        }
    }
}

@Composable
fun BuyAgainRow(
    orders: List<Order>,
    onBookClick: (Long) -> Unit,
    onAddToCart: (Long) -> Unit
) {
    val allBooks = orders.flatMap { it.items.map { item -> item.book } }.distinctBy { it.id }.take(6)
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(allBooks) { book ->
            BookCard(
                book = book,
                onClick = { onBookClick(book.id) },
                onAddToCart = { onAddToCart(book.id) }
            )
        }
    }
}

@Composable
fun GuestRecommendationsBanner(onLogin: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, null, modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.onSecondaryContainer)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Personalized Picks", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                Text("Login to see recommendations based on your reading history", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }
    }
}
