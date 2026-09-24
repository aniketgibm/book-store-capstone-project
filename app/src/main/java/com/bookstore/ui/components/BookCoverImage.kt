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
        val colors = listOf(
            Color(0xFF7E57C2), Color(0xFF26A69A), Color(0xFFEF5350),
            Color(0xFF42A5F5), Color(0xFFFF7043), Color(0xFF66BB6A)
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
