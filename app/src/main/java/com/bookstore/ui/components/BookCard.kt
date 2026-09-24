package com.bookstore.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bookstore.domain.model.Book
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onAddToCart: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(modifier = Modifier.height(180.dp).fillMaxWidth()) {
                com.bookstore.ui.components.BookCoverImage(
                    title = book.title,
                    coverUrl = book.coverImageUrl,
                    modifier = Modifier.fillMaxSize()
                )
                // Delivery badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(4.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
                ) {
                    Text(
                        text = book.deliveryDays,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = " ${book.rating}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatPrice(book.price),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                if (book.originalPrice > book.price) {
                    Text(
                        text = formatPrice(book.originalPrice),
                        style = MaterialTheme.typography.labelSmall.copy(
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (onAddToCart != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onAddToCart,
                        modifier = Modifier.fillMaxWidth().height(32.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Add", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}

fun formatPrice(price: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    format.maximumFractionDigits = 0
    return format.format(price)
}
