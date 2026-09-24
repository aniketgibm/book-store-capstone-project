package com.bookstore.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    // Handle both system back gesture and the top-bar arrow
    BackHandler(onBack = onNavigateBack)

    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var emailOffersEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
            SettingsSectionHeader("Notifications")

            SettingsToggleRow(
                title = "Push Notifications",
                subtitle = "Get updates on orders and offers",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            SettingsToggleRow(
                title = "Email Offers",
                subtitle = "Receive promotional emails",
                checked = emailOffersEnabled,
                onCheckedChange = { emailOffersEnabled = it }
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))

            SettingsSectionHeader("Appearance")

            SettingsToggleRow(
                title = "Dark Mode",
                subtitle = "Switch to dark theme",
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))

            SettingsSectionHeader("About")

            ListItem(
                headlineContent = { Text("App Version") },
                supportingContent = { Text("1.0.0") }
            )
            ListItem(
                headlineContent = { Text("Terms & Conditions") },
                supportingContent = { Text("View our terms of service") }
            )
            ListItem(
                headlineContent = { Text("Privacy Policy") },
                supportingContent = { Text("View our privacy policy") }
            )
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        trailingContent = {
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    )
}
