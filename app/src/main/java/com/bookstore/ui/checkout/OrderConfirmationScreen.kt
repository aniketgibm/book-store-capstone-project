package com.bookstore.ui.checkout

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.ui.components.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(
    orderId: Long,
    onNavigateToHome: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onCancelOrder: (Long) -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCancelDialog by remember { mutableStateOf(false) }

    // Pulse animation for checkmark
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Confirmed!") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E7D32),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Checkmark
            Surface(
                modifier = Modifier.size(96.dp).scale(scale),
                shape = CircleShape,
                color = Color(0xFF2E7D32)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text("Thank you for your order!", style = MaterialTheme.typography.headlineSmall, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Text("Order #$orderId has been placed successfully", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)

            // Order details card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Order Details", style = MaterialTheme.typography.titleMedium)
                    Divider()

                    uiState.cartItems.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${item.book.title} x${item.quantity}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                            Text(formatPrice(item.book.price * item.quantity), style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Divider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Paid", style = MaterialTheme.typography.titleSmall)
                        Text(formatPrice(uiState.total), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                    }

                    uiState.selectedAddress?.let { addr ->
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${addr.fullAddress}, ${addr.city} - ${addr.pincode}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Cancel notice
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("You can cancel this order within 48 hours of placing it.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onNavigateToHome,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Continue Shopping")
            }

            OutlinedButton(
                onClick = onNavigateToOrders,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("View My Orders")
            }

            TextButton(
                onClick = { showCancelDialog = true },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Cancel, null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Cancel This Order")
            }
        }
    }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancel Order?") },
            text = { Text("Are you sure you want to cancel order #$orderId? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        onCancelOrder(orderId)
                        showCancelDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Yes, Cancel")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showCancelDialog = false }) {
                    Text("Keep Order")
                }
            }
        )
    }
}
