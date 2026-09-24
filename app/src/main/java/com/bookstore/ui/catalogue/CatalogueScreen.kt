package com.bookstore.ui.catalogue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookstore.domain.model.Category
import com.bookstore.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogueScreen(
    onNavigateToProductList: (Long, String) -> Unit,
    onOpenDrawer: () -> Unit,
    onNavigateToSearch: () -> Unit,
    viewModel: CatalogueViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catalogue") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(Icons.Default.Search, "Search")
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
        if (uiState.categories.isEmpty()) {
            EmptyState(
                title = "No Categories",
                message = "Categories are loading...",
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onNavigateToProductList(category.id, category.name) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    // Premium palette: alternating navy-tint, gold-tint, burgundy-tint, neutral-warm
    val cardColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,    // pale navy
        MaterialTheme.colorScheme.secondaryContainer,  // pale gold
        MaterialTheme.colorScheme.tertiaryContainer,   // pale burgundy
        MaterialTheme.colorScheme.surfaceVariant,      // warm grey-beige
    )
    val textColors = listOf(
        MaterialTheme.colorScheme.onPrimaryContainer,
        MaterialTheme.colorScheme.onSecondaryContainer,
        MaterialTheme.colorScheme.onTertiaryContainer,
        MaterialTheme.colorScheme.onSurfaceVariant,
    )
    val colorIndex = category.name.length % cardColors.size

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColors[colorIndex]),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = category.iconEmoji,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                color = textColors[colorIndex]
            )
        }
    }
}
