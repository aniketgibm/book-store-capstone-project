package com.bookstore.ui.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.domain.model.Order
import com.bookstore.domain.model.OrderStatus
import com.bookstore.ui.components.EmptyState
import com.bookstore.ui.components.LoadingScreen
import com.bookstore.ui.components.formatPrice
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    onOpenDrawer: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToProductDetail: (Long) -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
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
                title = { Text("My Orders") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) { Icon(Icons.Default.Menu, "Menu") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> LoadingScreen(modifier = Modifier.padding(paddingValues))
            uiState.userId <= 0 -> EmptyState(
                title = "Sign in to view orders",
                message = "Login to see your order history",
                actionLabel = "Login",
                onAction = onNavigateToLogin,
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            )
            uiState.orders.isEmpty() -> EmptyState(
                title = "No orders yet",
                message = "Your order history will appear here once you make a purchase",
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            )
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.orders, key = { it.id }) { order ->
                        OrderCard(
                            order = order,
                            onBuyAgain = { viewModel.buyAgain(order.id) },
                            onCancelOrder = { viewModel.cancelOrder(order.id) },
                            onBookClick = onNavigateToProductDetail
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    onBuyAgain: () -> Unit,
    onCancelOrder: () -> Unit,
    onBookClick: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Order header
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Order #${order.id}", style = MaterialTheme.typography.titleSmall)
                    Text(
                        formatDate(order.placedAt),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "${order.items.size} item${if (order.items.size > 1) "s" else ""} • ${formatPrice(order.totalAmount)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    StatusChip(status = order.status)
                    Spacer(modifier = Modifier.height(4.dp))
                    IconButton(onClick = { expanded = !expanded }, modifier = Modifier.size(24.dp)) {
                        Icon(
                            if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Expanded items
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                order.items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { onBookClick(item.book.id) }, contentPadding = PaddingValues(0.dp)) {
                            Text(
                                "${item.book.title} x${item.quantity}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Text(formatPrice(item.unitPrice * item.quantity), style = MaterialTheme.typography.bodySmall)
                    }
                }
                if (order.discountAmount > 0) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Discount", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2E7D32))
                        Text("-${formatPrice(order.discountAmount)}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2E7D32))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action buttons
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onBuyAgain,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.Refresh, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Buy Again", style = MaterialTheme.typography.labelMedium)
                }
                if (order.canCancel) {
                    OutlinedButton(
                        onClick = { showCancelDialog = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.Cancel, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cancel", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancel Order?") },
            text = { Text("Cancel order #${order.id}? This cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = { onCancelOrder(); showCancelDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Yes, Cancel") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showCancelDialog = false }) { Text("Keep") }
            }
        )
    }
}

@Composable
fun StatusChip(status: OrderStatus) {
    val (color, label) = when (status) {
        OrderStatus.CONFIRMED -> Pair(Color(0xFF1565C0), "Confirmed")
        OrderStatus.SHIPPED -> Pair(Color(0xFFE65100), "Shipped")
        OrderStatus.DELIVERED -> Pair(Color(0xFF2E7D32), "Delivered")
        OrderStatus.CANCELLED -> Pair(Color(0xFFB71C1C), "Cancelled")
        OrderStatus.PENDING -> Pair(Color(0xFF6A1B9A), "Pending")
    }
    Surface(shape = RoundedCornerShape(4.dp), color = color.copy(alpha = 0.1f)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

fun formatDate(timestamp: Long): String {
    return SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.getDefault()).format(Date(timestamp))
}
