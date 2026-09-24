package com.bookstore.ui.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.ui.components.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onNavigateBack: () -> Unit,
    onPaymentSuccess: (Long) -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var useGiftPoints by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.placedOrderId) {
        uiState.placedOrderId?.let { onPaymentSuccess(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Order Summary
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Order Summary", style = MaterialTheme.typography.titleMedium)
                    Divider()
                    uiState.cartItems.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${item.book.title} x${item.quantity}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                            Text(formatPrice(item.book.price * item.quantity), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Divider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Delivery", style = MaterialTheme.typography.bodySmall)
                        Text(if (uiState.deliveryFee == 0.0) "FREE" else formatPrice(uiState.deliveryFee), style = MaterialTheme.typography.bodySmall)
                    }
                    if (uiState.giftPointsDiscount > 0) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Gift Points Discount", style = MaterialTheme.typography.bodySmall, color = com.bookstore.ui.theme.SuccessGreen)
                            Text("-${formatPrice(uiState.giftPointsDiscount)}", style = MaterialTheme.typography.bodySmall, color = com.bookstore.ui.theme.SuccessGreen)
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", style = MaterialTheme.typography.titleSmall)
                        Text(formatPrice(uiState.total), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            // Delivery address info
            uiState.selectedAddress?.let { addr ->
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(addr.label, style = MaterialTheme.typography.labelMedium)
                            Text("${addr.fullAddress}, ${addr.city} - ${addr.pincode}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Gift Points
            if (uiState.availableGiftPoints >= 100) {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("🎁 Gift Points", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                            Text("You have ${uiState.availableGiftPoints} points (${formatPrice(uiState.availableGiftPoints * 0.10)} value)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                            if (useGiftPoints && uiState.giftPointsDiscount > 0) {
                                Text("Saving ${formatPrice(uiState.giftPointsDiscount)}!", style = MaterialTheme.typography.bodySmall, color = com.bookstore.ui.theme.SuccessGreen)
                            }
                        }
                        Switch(
                            checked = useGiftPoints,
                            onCheckedChange = { checked ->
                                useGiftPoints = checked
                                viewModel.toggleGiftPoints(checked)
                            }
                        )
                    }
                }
            }

            // Credit Card Section
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CreditCard, null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Credit / Debit Card", style = MaterialTheme.typography.titleMedium)
                    }

                    OutlinedTextField(
                        value = uiState.cardNumber,
                        onValueChange = { if (it.length <= 16) viewModel.updateCardNumber(it) },
                        label = { Text("Card Number") },
                        placeholder = { Text("1234 5678 9012 3456") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.CreditCard, null) }
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = uiState.cardExpiry,
                            onValueChange = { if (it.length <= 5) viewModel.updateCardExpiry(it) },
                            label = { Text("MM/YY") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = uiState.cardCvv,
                            onValueChange = { if (it.length <= 3) viewModel.updateCardCvv(it) },
                            label = { Text("CVV") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            }

            // Error
            if (uiState.errorMessage != null) {
                Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            // Pay button
            Button(
                onClick = { viewModel.processPaymentAndPlaceOrder() },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                enabled = !uiState.isProcessingPayment && uiState.selectedAddress != null
            ) {
                if (uiState.isProcessingPayment) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Processing...")
                } else {
                    Icon(Icons.Default.Lock, null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pay ${formatPrice(uiState.total)}", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
