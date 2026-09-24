package com.bookstore.ui.cart

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.domain.model.CartItem
import com.bookstore.ui.components.BookCard
import com.bookstore.ui.components.BookCoverImage
import com.bookstore.ui.components.EmptyState
import com.bookstore.ui.components.LoadingScreen
import com.bookstore.ui.components.SectionHeader
import com.bookstore.ui.components.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onNavigateToProductDetail: (Long) -> Unit,
    onNavigateToCheckout: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onOpenDrawer: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
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
                title = { Text("My Cart (${uiState.cartItems.size})") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) { Icon(Icons.Default.Menu, "Menu") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            if (uiState.cartItems.isNotEmpty()) {
                Surface(shadowElevation = 8.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal", style = MaterialTheme.typography.bodyMedium)
                            Text(formatPrice(uiState.subtotal), style = MaterialTheme.typography.bodyMedium)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Delivery", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                if (uiState.deliveryFee == 0.0) "FREE" else formatPrice(uiState.deliveryFee),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (uiState.deliveryFee == 0.0) com.bookstore.ui.theme.SuccessGreen else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", style = MaterialTheme.typography.titleMedium)
                            Text(formatPrice(uiState.total), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                if (uiState.isGuest || uiState.userId <= 0) onNavigateToLogin()
                                else onNavigateToCheckout()
                            },
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text(if (uiState.isGuest || uiState.userId <= 0) "Login to Checkout" else "Proceed to Checkout")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> LoadingScreen(modifier = Modifier.padding(paddingValues))
            uiState.isGuest || uiState.userId <= 0 -> {
                EmptyState(
                    title = "Your Cart",
                    message = "Login to see your cart items and make purchases",
                    actionLabel = "Login",
                    onAction = onNavigateToLogin,
                    modifier = Modifier.padding(paddingValues).fillMaxSize()
                )
            }
            uiState.cartItems.isEmpty() -> {
                EmptyState(
                    title = "Your cart is empty",
                    message = "Browse our catalogue and add books you love",
                    modifier = Modifier.padding(paddingValues).fillMaxSize()
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.cartItems, key = { it.id }) { cartItem ->
                        CartItemRow(
                            cartItem = cartItem,
                            onRemove = { viewModel.removeItem(cartItem.id) },
                            onQuantityChange = { viewModel.updateQuantity(cartItem.id, it) },
                            onClick = { onNavigateToProductDetail(cartItem.bookId) }
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                    if (uiState.recommendations.isNotEmpty()) {
                        item {
                            SectionHeader(title = "You May Also Like")
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(uiState.recommendations) { book ->
                                    BookCard(
                                        book = book,
                                        onClick = { onNavigateToProductDetail(book.id) },
                                        onAddToCart = { viewModel.addToCart(book.id) }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onRemove: () -> Unit,
    onQuantityChange: (Int) -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(80.dp, 100.dp)) {
            BookCoverImage(
                title = cartItem.book.title,
                coverUrl = cartItem.book.coverImageUrl,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(cartItem.book.title, style = MaterialTheme.typography.titleSmall, maxLines = 2)
            Text(cartItem.book.author, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(formatPrice(cartItem.book.price), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                FilledTonalIconButton(onClick = { if (cartItem.quantity > 1) onQuantityChange(cartItem.quantity - 1) }, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Remove, null, modifier = Modifier.size(14.dp))
                }
                Text(cartItem.quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp), style = MaterialTheme.typography.bodyMedium)
                FilledTonalIconButton(onClick = { onQuantityChange(cartItem.quantity + 1) }, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(14.dp))
                }
            }
        }
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, "Remove", tint = MaterialTheme.colorScheme.error)
        }
    }
}
