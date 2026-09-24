package com.bookstore.ui.navigation

sealed class Screen(val route: String) {
    // Auth
    object Login : Screen("login")
    object Register : Screen("register")

    // Main
    object Home : Screen("home")
    object Catalogue : Screen("catalogue")
    object Cart : Screen("cart")
    object Orders : Screen("orders")
    object Profile : Screen("profile")

    // Detail
    object ProductList : Screen("product_list/{categoryId}/{categoryName}") {
        fun createRoute(categoryId: Long, categoryName: String) =
            "product_list/$categoryId/${categoryName}"
    }
    object ProductDetail : Screen("product_detail/{bookId}") {
        fun createRoute(bookId: Long) = "product_detail/$bookId"
    }

    // Checkout
    object CheckoutAddress : Screen("checkout_address")
    object CheckoutPayment : Screen("checkout_payment")
    object OrderConfirmation : Screen("order_confirmation/{orderId}") {
        fun createRoute(orderId: Long) = "order_confirmation/$orderId"
    }

    // Search
    object Search : Screen("search")

    // Drawer-only screens
    object Wishlist : Screen("wishlist")
    object Settings : Screen("settings")
}

// Bottom nav tabs
sealed class BottomNavItem(
    val screen: Screen,
    val label: String,
    val iconName: String
) {
    object Home : BottomNavItem(Screen.Home, "Home", "home")
    object Catalogue : BottomNavItem(Screen.Catalogue, "Catalogue", "menu_book")
    object Cart : BottomNavItem(Screen.Cart, "Cart", "shopping_cart")
    object Orders : BottomNavItem(Screen.Orders, "Orders", "receipt_long")

    companion object {
        val items = listOf(Home, Catalogue, Cart, Orders)
    }
}
