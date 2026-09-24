package com.bookstore.ui.navigation

import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// Simple ViewModel to check initial session state
@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : ViewModel() {
    val startDestination: StateFlow<String?> = sessionDataStore.currentUserId
        .map { userId ->
            if (userId != null && userId > 0) Screen.Home.route
            else if (userId == -1L) Screen.Home.route // guest
            else Screen.Login.route
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    sessionViewModel: SessionViewModel = hiltViewModel()
) {
    val startDestination by sessionViewModel.startDestination.collectAsState()

    if (startDestination == null) {
        // Splash / loading
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val bottomNavRoutes = BottomNavItem.items.map { it.screen.route }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBottomNav = currentRoute in bottomNavRoutes

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    navController = navController,
                    drawerState = drawerState
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
                                    navController.navigate(item.screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
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

                // Main screens
                composable(Screen.Home.route) {
                    HomeScreen(
                        onNavigateToProductDetail = { navController.navigate(Screen.ProductDetail.createRoute(it)) },
                        onNavigateToCatalogue = { navController.navigate(Screen.Catalogue.route) },
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
                        onNavigateToCart = { navController.navigate(Screen.Cart.route) },
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
                            navController.navigate(Screen.Orders.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        },
                        onCancelOrder = { /* handled inline */ }
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
}

@Composable
fun DrawerContent(
    navController: androidx.navigation.NavController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxHeight().padding(vertical = 24.dp)) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp).clip(CircleShape),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.AutoMirrored.Filled.MenuBook, null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(28.dp))
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("BookStore", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    Text("Your world of books", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") },
            selected = false,
            onClick = {
                navController.navigate(Screen.Profile.route)
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.AutoMirrored.Filled.ReceiptLong, null) },
            label = { Text("My Orders") },
            selected = false,
            onClick = {
                navController.navigate(Screen.Orders.route)
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Favorite, null) },
            label = { Text("Wishlist (Coming Soon)") },
            selected = false,
            onClick = { scope.launch { drawerState.close() } },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Settings, null) },
            label = { Text("Settings") },
            selected = false,
            onClick = { scope.launch { drawerState.close() } },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Divider(modifier = Modifier.padding(horizontal = 12.dp))

        NavigationDrawerItem(
            icon = { Icon(Icons.AutoMirrored.Filled.Logout, null, tint = MaterialTheme.colorScheme.error) },
            label = { Text("Logout", color = MaterialTheme.colorScheme.error) },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
