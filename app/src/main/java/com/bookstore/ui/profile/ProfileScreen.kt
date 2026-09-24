package com.bookstore.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToLogin: () -> Unit,
    onOpenDrawer: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) onNavigateToLogin()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
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
            uiState.user == null -> EmptyState(
                title = "Not logged in",
                message = "Login to view your profile",
                actionLabel = "Login",
                onAction = onNavigateToLogin,
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            )
            else -> {
                val user = uiState.user!!
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    item {
                        // Profile header
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                modifier = Modifier.size(80.dp).clip(CircleShape),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        user.name.take(2).uppercase(),
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(user.name, style = MaterialTheme.typography.headlineSmall)
                            Text(user.email, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            Spacer(modifier = Modifier.height(12.dp))

                            // Gift points
                            Surface(
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Star, null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("${user.giftPoints} Gift Points", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                }
                            }
                        }
                    }

                    item {
                        Divider()
                        Text("Saved Addresses", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))
                    }

                    if (uiState.addresses.isEmpty()) {
                        item {
                            Text("No addresses saved", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        items(uiState.addresses) { address ->
                            ListItem(
                                headlineContent = { Text(address.label) },
                                supportingContent = { Text("${address.fullAddress}, ${address.city} - ${address.pincode}") },
                                leadingContent = { Icon(Icons.Default.LocationOn, null) }
                            )
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = { viewModel.logout() },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Logout, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Logout")
                        }
                    }
                }
            }
        }
    }
}
