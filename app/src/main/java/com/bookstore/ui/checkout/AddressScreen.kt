package com.bookstore.ui.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.domain.model.Address

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPayment: () -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddAddressSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Delivery Address") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = onNavigateToPayment,
                    modifier = Modifier.fillMaxWidth().padding(16.dp).height(50.dp),
                    enabled = uiState.selectedAddress != null
                ) {
                    Text("Continue to Payment")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.addresses) { address ->
                AddressCard(
                    address = address,
                    isSelected = uiState.selectedAddress?.id == address.id,
                    onSelect = { viewModel.selectAddress(address) }
                )
            }
            item {
                OutlinedButton(
                    onClick = { showAddAddressSheet = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add New Address")
                }
            }
        }
    }

    if (showAddAddressSheet) {
        AddAddressBottomSheet(
            userId = uiState.userId,
            onDismiss = { showAddAddressSheet = false },
            onSave = { address ->
                viewModel.addNewAddress(address)
                showAddAddressSheet = false
            }
        )
    }
}

@Composable
fun AddressCard(address: Address, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        onClick = onSelect,
        modifier = Modifier.fillMaxWidth(),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = isSelected, onClick = onSelect)
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row {
                    Text(address.label, style = MaterialTheme.typography.titleSmall)
                    if (address.isDefault) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(shape = RoundedCornerShape(4.dp), color = MaterialTheme.colorScheme.secondaryContainer) {
                            Text("Default", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                        }
                    }
                }
                Text(address.fullAddress, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("${address.city} - ${address.pincode}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressBottomSheet(
    userId: Long,
    onDismiss: () -> Unit,
    onSave: (Address) -> Unit
) {
    var label by remember { mutableStateOf("Home") }
    var fullAddress by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(16.dp).padding(bottom = 32.dp)) {
            Text("Add New Address", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = label, onValueChange = { label = it }, label = { Text("Label (Home/Work)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = fullAddress, onValueChange = { fullAddress = it }, label = { Text("Street Address") }, modifier = Modifier.fillMaxWidth(), maxLines = 2)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = pincode, onValueChange = { if (it.length <= 6) pincode = it }, label = { Text("Pincode") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onSave(Address(userId = userId, label = label, fullAddress = fullAddress, city = city, pincode = pincode))
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = fullAddress.isNotBlank() && city.isNotBlank() && pincode.length == 6
            ) {
                Text("Save Address")
            }
        }
    }
}
