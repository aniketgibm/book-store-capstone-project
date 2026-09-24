package com.bookstore.ui.catalogue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.ui.components.BookCard
import com.bookstore.ui.components.BookCoverImage
import com.bookstore.ui.components.EmptyState
import com.bookstore.ui.components.LoadingScreen
import com.bookstore.ui.components.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    bookId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit,
    onRelatedBookClick: (Long) -> Unit,
    viewModel: CatalogueViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var quantity by remember { mutableIntStateOf(1) }

    LaunchedEffect(bookId) {
        viewModel.loadBookDetail(bookId)
    }

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
                title = { Text(uiState.selectedBook?.title ?: "Book Detail", maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToCart) {
                        Icon(Icons.Default.ShoppingCart, "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            uiState.selectedBook?.let { book ->
                Surface(
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Quantity
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilledTonalIconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.Remove, null, modifier = Modifier.size(18.dp))
                            }
                            Text(
                                text = quantity.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.widthIn(min = 24.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            FilledTonalIconButton(
                                onClick = { if (quantity < (book.stockCount)) quantity++ },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                            }
                        }

                        Button(
                            onClick = { viewModel.addToCart(book.id, quantity) },
                            modifier = Modifier.weight(1f).height(48.dp)
                        ) {
                            Icon(Icons.Default.AddShoppingCart, null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add to Cart")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> LoadingScreen(modifier = Modifier.padding(paddingValues))
            uiState.selectedBook == null -> EmptyState(
                title = "Book Not Found",
                message = "This book is not available",
                actionLabel = "Go Back",
                onAction = onNavigateBack,
                modifier = Modifier.padding(paddingValues)
            )
            else -> {
                val book = uiState.selectedBook!!
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        // Cover image
                        Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                            BookCoverImage(
                                title = book.title,
                                coverUrl = book.coverImageUrl,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    item {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(book.title, style = MaterialTheme.typography.headlineSmall)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(book.author, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(formatPrice(book.price), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                                    if (book.originalPrice > book.price) {
                                        Text(
                                            formatPrice(book.originalPrice),
                                            style = MaterialTheme.typography.bodySmall.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        val discount = ((book.originalPrice - book.price) / book.originalPrice * 100).toInt()
                                        Text("${discount}% OFF", style = MaterialTheme.typography.labelSmall, color = com.bookstore.ui.theme.SuccessGreen)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Rating
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                repeat(5) { index ->
                                    Icon(
                                        imageVector = if (index < book.rating.toInt()) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = null,
                                        tint = com.bookstore.ui.theme.StarGold,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("${book.rating} (${book.ratingCount} reviews)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Delivery & Brand info
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                AssistChip(
                                    onClick = {},
                                    label = { Text("🚚 ${book.deliveryDays}", style = MaterialTheme.typography.labelSmall) }
                                )
                                AssistChip(
                                    onClick = {},
                                    label = { Text("📦 ${book.brand}", style = MaterialTheme.typography.labelSmall) }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Description
                            Text("About this book", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(book.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            if (book.isbn.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("ISBN: ${book.isbn}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }

                    // Related Books
                    if (uiState.relatedBooks.isNotEmpty()) {
                        item {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Related Books",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(uiState.relatedBooks) { related ->
                                    BookCard(
                                        book = related,
                                        onClick = { onRelatedBookClick(related.id) },
                                        onAddToCart = { viewModel.addToCart(related.id) }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
