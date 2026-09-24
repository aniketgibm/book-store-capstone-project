package com.bookstore.ui.theme

import androidx.compose.ui.graphics.Color

// ── Light Scheme ──────────────────────────────────────────────────────────────
// Primary: Deep Ink Navy — authoritative, premium, trustworthy
val Primary          = Color(0xFF0D1B3E)   // deep navy
val OnPrimary        = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFD6E2FF)   // pale sky blue tint
val OnPrimaryContainer = Color(0xFF001258)

// Secondary: Champagne Gold — premium accent, call-to-action highlights
val Secondary          = Color(0xFFC9A84C)   // champagne gold
val OnSecondary        = Color(0xFF1A1100)
val SecondaryContainer = Color(0xFFFFF0CB)   // very pale gold
val OnSecondaryContainer = Color(0xFF251A00)

// Tertiary: Warm Burgundy — for badges, tags, accents
val Tertiary          = Color(0xFF7B2D42)
val OnTertiary        = Color(0xFFFFFFFF)
val TertiaryContainer = Color(0xFFFFD9E2)
val OnTertiaryContainer = Color(0xFF31001C)

// Surfaces & Backgrounds: warm off-white linen — not stark white, feels premium
val Background       = Color(0xFFFAF8F5)   // warm off-white linen
val OnBackground     = Color(0xFF1A1C20)
val Surface          = Color(0xFFFAF8F5)
val OnSurface        = Color(0xFF1A1C20)
val SurfaceVariant   = Color(0xFFE8E4DC)   // warm grey-beige
val OnSurfaceVariant = Color(0xFF46443C)
val Outline          = Color(0xFF78756C)
val OutlineVariant   = Color(0xFFCAC7BE)

// Semantic
val Error   = Color(0xFFBA1A1A)
val OnError = Color(0xFFFFFFFF)

// Utility (used directly in composables)
val SuccessGreen  = Color(0xFF2E7D32)
val WarningAmber  = Color(0xFFE65100)
val StarGold      = Color(0xFFF9A825)   // star ratings

// ── Dark Scheme ───────────────────────────────────────────────────────────────
// Deep charcoal backgrounds with champagne gold primary — ultra-premium night mode
val DarkPrimary          = Color(0xFFC9A84C)   // champagne gold becomes primary in dark
val DarkOnPrimary        = Color(0xFF1A1100)
val DarkPrimaryContainer = Color(0xFF3D2F00)
val DarkOnPrimaryContainer = Color(0xFFFFDF9E)

val DarkSecondary          = Color(0xFFB0C6FF)
val DarkOnSecondary        = Color(0xFF002381)
val DarkSecondaryContainer = Color(0xFF0A3494)
val DarkOnSecondaryContainer = Color(0xFFDAE2FF)

val DarkTertiary          = Color(0xFFFFB1C8)
val DarkOnTertiary        = Color(0xFF4A0021)
val DarkTertiaryContainer = Color(0xFF63162E)
val DarkOnTertiaryContainer = Color(0xFFFFD9E2)

val DarkBackground   = Color(0xFF111318)   // deep charcoal
val DarkOnBackground = Color(0xFFE2E2E9)
val DarkSurface      = Color(0xFF111318)
val DarkOnSurface    = Color(0xFFE2E2E9)
val DarkSurfaceVariant   = Color(0xFF2C2C34)
val DarkOnSurfaceVariant = Color(0xFFC8C6BE)
val DarkOutline          = Color(0xFF928F88)
