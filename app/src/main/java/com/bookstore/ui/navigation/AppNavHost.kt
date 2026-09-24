package com.bookstore.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.bookstore.data.local.SessionDataStore
import com.bookstore.ui.auth.*
import com.bookstore.ui.cart.CartScreen
import com.bookstore.ui.catalogue.*
import com.bookstore.ui.checkout.*
import com.bookstore.ui.home.HomeScreen
import com.bookstore.ui.orders.OrderHistoryScreen
import com.bookstore.ui.profile.*
import com.bookstore.ui.search.SearchScreen
import com.bookstore.ui.settings.SettingsScreen
import com.bookstore.ui.wishlist.WishlistScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel to resolve the initial start destination from persisted session
@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : ViewModel() {
    val startDestination: StateFlow<String?> = sessionDataStore.currentUserId
        .map { userId ->
            when {
                userId != null && userId > 0 -> Screen.Home.route
                userId == -1L -> Screen.Home.route // guest
                else -> Screen.Login.route
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}

// Routes that belong to the bottom nav (used for selection highlight & bottom bar visibility)
private val bottomNavRoutes = BottomNavItem.items.map { it.screen.route }

// Bottom bar navigation: pop back to start saving state so tabs remember their scroll/selection,
// and restore it when the same tab is re-selected.
private fun NavController.navigateBottomNav(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

// Drawer navigation strategy:
//
// All drawer taps first clear every back-stack entry EXCEPT the graph root (home),
// discarding any previously saved state. The destination is then placed on top of home.
// This gives us two invariants:
//   1. Home always stays as the base — so the back button can pop back to it.
//   2. No saved state is ever restored when the user taps a bottom-nav tab afterwards,
//      because saveState=false prevents anything being written to the state store.
//
// For bottom-nav sibling destinations (Orders) that the drawer also exposes we use the
// same approach so their tab highlights correctly once the user is there.
private fun NavController.navigateFromDrawer(route: String) {
    navigate(route) {
        // Pop everything above home (inclusive=false keeps home itself)
        // saveState=false ensures nothing is written to the saved-state store,
        // so bottom-nav restoreState=true never replays a stale drawer screen.
        popUpTo(graph.startDestinationId) {
            inclusive = false
            saveState = false
        }
        launchSingleTop = true
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    sessionViewModel: SessionViewModel = hiltViewModel()
) {
    val startDestination by sessionViewModel.startDestination.collectAsState()

    if (startDestination == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBottomNav = currentRoute in bottomNavRoutes

    // On API 35+, window.statusBarColor is fully ignored by the system.
    // The only reliable way to colour the status bar is to draw behind its inset
    // in Compose. The outer Box fills the entire screen with the primary colour;
    // the inner Box offsets all content below the status bar with a background
    // colour, leaving only the status-bar strip showing the primary colour.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    navController = navController,
                    drawerState = drawerState,
                    currentRoute = currentRoute
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                if (showBottomNav) {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        BottomNavItem.items.forEach { item ->
                            NavigationBarItem(
                                icon = {
                                    when (item) {
                                        is BottomNavItem.Home -> Icon(Icons.Default.Home, item.label)
                                        is BottomNavItem.Catalogue -> Icon(Icons.AutoMirrored.Filled.MenuBook, item.label)
                                        is BottomNavItem.Cart -> Icon(Icons.Default.ShoppingCart, item.label)
                                        is BottomNavItem.Orders -> Icon(Icons.AutoMirrored.Filled.ReceiptLong, item.label)
                                    }
                                },
                                label = { Text(item.label) },
                                selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                                onClick = {
                                    navController.navigateBottomNav(item.screen.route)
                                }
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = startDestination!!,
                modifier = Modifier.padding(paddingValues)
            ) {
                // Auth
                composable(Screen.Login.route) {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                        onContinueAsGuest = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Screen.Register.route) {
                    RegisterScreen(
                        onRegisterSuccess = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onNavigateToLogin = { navController.popBackStack() }
                    )
                }

                // Bottom-nav main screens
                composable(Screen.Home.route) {
                    HomeScreen(
                        onNavigateToProductDetail = { navController.navigate(Screen.ProductDetail.createRoute(it)) },
                        onNavigateToCatalogue = { navController.navigateBottomNav(Screen.Catalogue.route) },
                        onNavigateToCategory = { id, name -> navController.navigate(Screen.ProductList.createRoute(id, name)) },
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        onNavigateToSearch = { navController.navigate(Screen.Search.route) }
                    )
                }

                composable(Screen.Catalogue.route) {
                    CatalogueScreen(
                        onNavigateToProductList = { id, name -> navController.navigate(Screen.ProductList.createRoute(id, name)) },
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        onNavigateToSearch = { navController.navigate(Screen.Search.route) }
                    )
                }

                composable(Screen.Cart.route) {
                    CartScreen(
                        onNavigateToProductDetail = { navController.navigate(Screen.ProductDetail.createRoute(it)) },
                        onNavigateToCheckout = { navController.navigate(Screen.CheckoutAddress.route) },
                        onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }

                composable(Screen.Orders.route) {
                    OrderHistoryScreen(
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                        onNavigateToProductDetail = { navController.navigate(Screen.ProductDetail.createRoute(it)) }
                    )
                }

                composable(Screen.Profile.route) {
                    ProfileScreen(
                        onNavigateToLogin = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }

                // Drawer-only screens (no bottom bar, back returns to previous screen)
                composable(Screen.Wishlist.route) {
                    WishlistScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                // Product list
                composable(Screen.ProductList.route) { backStack ->
                    val categoryId = backStack.arguments?.getString("categoryId")?.toLongOrNull() ?: 0L
                    val categoryName = backStack.arguments?.getString("categoryName") ?: ""
                    ProductListScreen(
                        categoryId = categoryId,
                        categoryName = categoryName,
                        onNavigateToProductDetail = { navController.navigate(Screen.ProductDetail.createRoute(it)) },
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                // Product detail
                composable(Screen.ProductDetail.route) { backStack ->
                    val bookId = backStack.arguments?.getString("bookId")?.toLongOrNull() ?: 0L
                    ProductDetailScreen(
                        bookId = bookId,
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateToCart = { navController.navigateBottomNav(Screen.Cart.route) },
                        onRelatedBookClick = { navController.navigate(Screen.ProductDetail.createRoute(it)) }
                    )
                }

                // Checkout flow
                composable(Screen.CheckoutAddress.route) {
                    AddressScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateToPayment = { navController.navigate(Screen.CheckoutPayment.route) }
                    )
                }

                composable(Screen.CheckoutPayment.route) {
                    PaymentScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onPaymentSuccess = { orderId ->
                            navController.navigate(Screen.OrderConfirmation.createRoute(orderId)) {
                                popUpTo(Screen.Cart.route) { inclusive = false }
                            }
                        }
                    )
                }

                composable(Screen.OrderConfirmation.route) { backStack ->
                    val orderId = backStack.arguments?.getString("orderId")?.toLongOrNull() ?: 0L
                    OrderConfirmationScreen(
                        orderId = orderId,
                        onNavigateToHome = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        onNavigateToOrders = {
                            navController.navigateBottomNav(Screen.Orders.route)
                        },
                        onCancelOrder = { /* handled inline in confirmation screen */ }
                    )
                }

                // Search
                composable(Screen.Search.route) {
                    SearchScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateToProductDetail = { navController.navigate(Screen.ProductDetail.createRoute(it)) }
                    )
                }
            }
        }
    }
    } // inner statusBarsPadding Box
    } // outer primary-colour Box
}

@Composable
fun DrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    currentRoute: String?
) {
    val scope = rememberCoroutineScope()

    // All drawer navigation uses navigateFromDrawer — clears the full back stack
    // with no saveState so bottom-nav tabs always show a clean screen afterwards.
    fun navigateAndClose(destination: String) {
        scope.launch {
            drawerState.close()
            navController.navigateFromDrawer(destination)
        }
    }

    Column(modifier = Modifier.fillMaxHeight().padding(vertical = 24.dp)) {
        // App header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp).clip(CircleShape),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    "BookStore",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Your world of books",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Profile
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = currentRoute == Screen.Profile.route,
            onClick = { navigateAndClose(Screen.Profile.route) },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        // My Orders
        NavigationDrawerItem(
            icon = { Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = null) },
            label = { Text("My Orders") },
            selected = currentRoute == Screen.Orders.route,
            onClick = { navigateAndClose(Screen.Orders.route) },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        // Wishlist — now navigates to its own screen
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("Wishlist") },
            selected = currentRoute == Screen.Wishlist.route,
            onClick = { navigateAndClose(Screen.Wishlist.route) },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        // Settings — now navigates to its own screen
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = currentRoute == Screen.Settings.route,
            onClick = { navigateAndClose(Screen.Settings.route) },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))

        // Logout
        NavigationDrawerItem(
            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            label = { Text("Logout", color = MaterialTheme.colorScheme.error) },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
