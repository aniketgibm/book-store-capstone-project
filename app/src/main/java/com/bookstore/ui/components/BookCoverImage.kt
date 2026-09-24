package com.bookstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun BookCoverImage(
    title: String,
    coverUrl: String,
    modifier: Modifier = Modifier
) {
    if (coverUrl.isNotEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        )
    } else {
        // Premium muted palette — deep navy, teal, burgundy, slate, forest, indigo
        val colors = listOf(
            Color(0xFF0D1B3E),  // deep navy
            Color(0xFF1A3A4A),  // deep teal
            Color(0xFF7B2D42),  // warm burgundy
            Color(0xFF2C3E50),  // dark slate
            Color(0xFF1B4332),  // deep forest
            Color(0xFF2D1B69),  // deep indigo
        )
        val color = colors[title.length % colors.size]
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title.take(12),
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}
