# BookStore Android App — Implementation Plan

## Top-Level Overview

Build a full-featured Android bookstore app using **Kotlin + Jetpack Compose**, following **Clean Architecture (MVVM)** with a **Room DB** local data source initially, designed so the data layer can be swapped to a REST API (Retrofit) with minimal changes later.

### Scope
- Authentication (Login / Register) — login required only at checkout for guest users
- Home Screen with featured books, promotions, recommendations based on order history
- Catalogue Screen with category browsing, brand browsing, product detail, related products
- Shopping Cart with order-history-based recommendations
- Checkout flow: address selection → payment (Credit Card + Gift Points) → confirmation
- Order History with "Buy Again" feature
- Order cancellation within 48 hours
- Rich Room DB seed data: 5 categories × ~5 books + 2 users with order history + gift points
- Bottom Navigation (primary) + Navigation Drawer (secondary/profile)
- Unit tests for all business logic (ViewModels + UseCases)

### Out of Scope
- Real payment gateway integration (mock simulation only)
- Backend / server-side implementation
- Push notifications

### Tech Stack
| Layer | Technology |
|---|---|
| UI | Jetpack Compose, Material 3 |
| Navigation | Compose Navigation |
| State | ViewModel + StateFlow |
| DI | Hilt |
| Local DB | Room |
| Images | Coil |
| Testing | JUnit 4, MockK, Turbine (Flow testing) |
| Build | Gradle (Kotlin DSL), minSdk 24, targetSdk 34 |

---

## Project Structure

```
app/
└── src/
    ├── main/
    │   ├── java/com/bookstore/
    │   │   ├── data/
    │   │   │   ├── local/           # Room DB, DAOs, Entities
    │   │   │   ├── remote/          # Retrofit interfaces (stub, future)
    │   │   │   ├── repository/      # Repository implementations
    │   │   │   └── mapper/          # Entity <-> Domain model mappers
    │   │   ├── domain/
    │   │   │   ├── model/           # Domain models (pure Kotlin data classes)
    │   │   │   ├── repository/      # Repository interfaces
    │   │   │   └── usecase/         # One class per use-case
    │   │   ├── ui/
    │   │   │   ├── auth/            # Login, Register screens + VM
    │   │   │   ├── home/            # Home screen + VM
    │   │   │   ├── catalogue/       # Category list, product list, product detail + VMs
    │   │   │   ├── cart/            # Cart screen + VM
    │   │   │   ├── checkout/        # Address, Payment, Confirmation + VMs
    │   │   │   ├── orders/          # Order history + VM
    │   │   │   ├── profile/         # Profile / drawer + VM
    │   │   │   ├── navigation/      # NavGraph, BottomNavBar, Drawer
    │   │   │   └── theme/           # MaterialTheme, Typography, Colors
    │   │   ├── di/                  # Hilt modules
    │   │   └── BookStoreApp.kt      # Application class
    │   └── res/
    └── test/                        # Unit tests
```

---

## Sub-Tasks

### Sub-Task 1 — Android Project Scaffold & Build Configuration
**Status:** `[ ] pending`

**Intent**  
Create the Gradle project skeleton so all subsequent sub-tasks can build on a compiling base. Establishes dependency versions, Hilt setup, and Room/Compose configuration.

**Expected Outcomes**
- `./gradlew build` succeeds with an empty Compose app
- Hilt DI is wired and compiles
- Room, Compose, Navigation, Coil, MockK, Turbine dependencies are all present
- `BookStoreApp` (Hilt Application) is registered in AndroidManifest

**Todo List**
1. Create `settings.gradle.kts` and root `build.gradle.kts` with version catalog or `libs.versions.toml`
2. Create `app/build.gradle.kts` with:
   - `minSdk 24`, `targetSdk 34`, `compileSdk 34`
   - Kotlin + Compose compiler plugin
   - Hilt Gradle plugin + kapt
   - All required dependencies (Compose BOM, Navigation-Compose, Room, Hilt, Coil, Coroutines, JUnit 4, MockK, Turbine)
3. Create `BookStoreApp.kt` annotated with `@HiltAndroidApp`
4. Create `MainActivity.kt` as the single-activity host annotated with `@AndroidEntryPoint`
5. Create placeholder `MainNavHost.kt` in `ui/navigation/`
6. Verify `./gradlew assembleDebug` succeeds

**Relevant Context**
- Greenfield project — no existing files to integrate
- Use Kotlin DSL throughout
- Hilt version: 2.51, Room version: 2.6.x, Compose BOM: 2024.x

---

### Sub-Task 2 — Domain Layer: Models, Repository Interfaces & Use Cases
**Status:** `[ ] pending`

**Intent**  
Define all domain models and repository contracts — the core business vocabulary. This layer has zero Android dependencies and drives both the data layer and the UI layer.

**Expected Outcomes**
- All domain models exist as pure Kotlin data classes
- All repository interfaces are defined
- All use-case classes are defined (one class per use case) with `operator fun invoke()`
- No Android or Room imports anywhere in `domain/`

**Todo List**
1. Create domain models in `domain/model/`:
   - `User(id, name, email, passwordHash, giftPoints, addresses: List<Address>)`
   - `Address(id, userId, label, fullAddress, isDefault)`
   - `Book(id, title, author, description, price, coverImageUrl, categoryId, brand, rating, deliveryDate, stockCount)`
   - `Category(id, name, iconUrl)`
   - `CartItem(id, bookId, quantity, book: Book)`
   - `Order(id, userId, items: List<OrderItem>, totalAmount, status, placedAt, canCancel)`
   - `OrderItem(id, orderId, book: Book, quantity, unitPrice)`
   - `PaymentResult(success, transactionId, message)`
2. Create repository interfaces in `domain/repository/`:
   - `AuthRepository`: `login()`, `register()`, `getCurrentUser()`, `logout()`
   - `BookRepository`: `getCategories()`, `getBooksByCategory()`, `getBookById()`, `getRelatedBooks()`, `getFeaturedBooks()`, `searchBooks()`
   - `CartRepository`: `getCartItems()`, `addToCart()`, `removeFromCart()`, `updateQuantity()`, `clearCart()`
   - `OrderRepository`: `getOrders()`, `placeOrder()`, `cancelOrder()`, `getOrderById()`
   - `UserRepository`: `getAddresses()`, `addAddress()`, `getUserGiftPoints()`
3. Create use cases in `domain/usecase/`:
   - Auth: `LoginUseCase`, `RegisterUseCase`, `LogoutUseCase`
   - Books: `GetCategoriesUseCase`, `GetBooksByCategoryUseCase`, `GetBookDetailUseCase`, `GetRelatedBooksUseCase`, `GetFeaturedBooksUseCase`, `GetRecommendedBooksUseCase` (based on order history)
   - Cart: `AddToCartUseCase`, `RemoveFromCartUseCase`, `GetCartUseCase`, `UpdateCartQuantityUseCase`
   - Order: `PlaceOrderUseCase`, `GetOrderHistoryUseCase`, `CancelOrderUseCase`, `BuyAgainUseCase`
   - Payment: `ProcessPaymentUseCase`, `RedeemGiftPointsUseCase`
   - User: `GetAddressesUseCase`, `AddAddressUseCase`

**Relevant Context**
- `GetRecommendedBooksUseCase` — finds categories from past orders, returns books in those categories not yet purchased
- `CancelOrderUseCase` — checks `order.placedAt` vs now; only cancels if < 48 hours
- `BuyAgainUseCase` — delegates to `AddToCartUseCase` for each item in a past order
- `canCancel` on `Order` is a derived field computed by the use case, not stored

---

### Sub-Task 3 — Data Layer: Room DB, Entities, DAOs & Repository Implementations
**Status:** `[ ] pending`

**Intent**  
Implement the Room database with all entities and DAOs, plus repository implementations that bridge Room to the domain layer. Design the `DataSource` abstraction so the Room implementation can be swapped for a Retrofit implementation later without touching the domain or UI layers.

**Expected Outcomes**
- Room database compiles and migrations are versioned
- All DAOs expose `Flow<>` return types for reactive UI
- Repository implementations satisfy all domain repository interfaces
- `DataSource` interfaces exist alongside `LocalDataSource` implementations
- Rich seed data is loaded via `RoomDatabase.Callback` `onCreate`

**Todo List**
1. Create Room entities in `data/local/entity/`:
   - Mirror domain models; add `@Entity`, `@PrimaryKey` annotations
   - `UserEntity`, `AddressEntity`, `BookEntity`, `CategoryEntity`, `CartItemEntity`, `OrderEntity`, `OrderItemEntity`
2. Create DAOs in `data/local/dao/`:
   - `UserDao`: `getUserByEmail()`, `insertUser()`, `updateGiftPoints()`
   - `BookDao`: `getAllCategories()`, `getBooksByCategory()`, `getBookById()`, `getRelatedBooks()`, `getFeaturedBooks()`
   - `CartDao`: `getCartItems(): Flow<List<CartItemEntity>>`, `insertCartItem()`, `deleteCartItem()`, `updateQuantity()`, `clearCart()`
   - `OrderDao`: `getOrdersForUser(): Flow<List<OrderEntity>>`, `insertOrder()`, `updateOrderStatus()`, `getOrderWithItems()`
   - `AddressDao`: `getAddressesForUser(): Flow<List<AddressEntity>>`, `insertAddress()`
3. Create `BookStoreDatabase` (`@Database`) wiring all entities, version 1
4. Create `DataSource` interfaces in `data/`:
   - `BookDataSource`, `AuthDataSource`, `CartDataSource`, `OrderDataSource`, `UserDataSource`
5. Create `LocalDataSource` implementations backed by DAOs
6. Create `RemoteDataSource` stub implementations (return empty / throw `NotImplementedError`) in `data/remote/` — placeholder for future API
7. Create mappers in `data/mapper/` for each Entity ↔ Domain model conversion
8. Implement all repository classes in `data/repository/` using `LocalDataSource`
9. Create seed data in `DatabaseSeeder.kt` (called from `RoomDatabase.Callback`):
   - 5 categories: Fiction, Non-Fiction, Science, Technology, Self-Help
   - 5 books per category (25 books total) with realistic titles, authors, prices
   - 2 users: `alice@bookstore.com / password123` (500 gift points), `bob@bookstore.com / password123` (200 gift points)
   - Each user has 2 past orders with 2–3 books each
   - Each user has 2 saved addresses

**Relevant Context**
- `CartItemEntity` uses `userId` FK so carts are per-user; guest cart stored with `userId = -1`
- `OrderEntity` stores `placedAt: Long` (epoch ms) — used by `CancelOrderUseCase`
- The `DataSource` interface is the swap point: replacing `LocalDataSource` with `RemoteDataSource` in the Hilt module is the only change needed to move to API

---

### Sub-Task 4 — Dependency Injection (Hilt Modules)
**Status:** `[ ] pending`

**Intent**  
Wire all Hilt modules so ViewModels can receive use cases and repositories via injection. This is the glue between layers.

**Expected Outcomes**
- Single-activity app compiles with Hilt
- All repositories, data sources, use cases, and ViewModels are injectable
- Database and DAO instances are `@Singleton`

**Todo List**
1. Create `DatabaseModule.kt` — provides `BookStoreDatabase`, all DAOs as `@Singleton`
2. Create `DataSourceModule.kt` — binds `LocalDataSource` implementations to `DataSource` interfaces
3. Create `RepositoryModule.kt` — binds repository implementations to domain repository interfaces
4. Create `UseCaseModule.kt` — provides all use-case instances (or rely on `@Inject constructor` directly)
5. Verify `./gradlew assembleDebug` still passes

**Relevant Context**
- Use `@InstallIn(SingletonComponent::class)` for DB/DAOs
- Use `@InstallIn(ViewModelComponent::class)` for use cases if scoped to ViewModel lifetime
- Repository implementations use `@Inject constructor` — no explicit module bindings needed if interfaces are bound with `@Binds`

---

### Sub-Task 5 — Navigation & App Shell (Bottom Nav + Drawer)
**Status:** `[ ] pending`

**Intent**  
Build the app shell: `MainNavHost` defining all routes, `BottomNavigationBar` (Home, Catalogue, Cart, Orders), and a `NavigationDrawer` (Profile, Settings, Logout). This scaffolding must exist before individual screens are built.

**Expected Outcomes**
- All route destinations are declared (even if screens are placeholder `Text()` initially)
- Bottom nav highlights the active tab
- Drawer opens via hamburger icon in the top app bar
- Auth routes (Login, Register) sit outside the bottom-nav scaffold
- Deep-link from Order History → Product Detail works via the nav graph

**Todo List**
1. Define all route strings/sealed class in `NavigationRoute.kt`
2. Build `AppNavHost.kt` with `NavHost` — separate `authNavGraph` and `mainNavGraph`
3. Create `MainScaffold.kt` wrapping `BottomNavigationBar` + `NavigationDrawer` + content slot
4. Implement `BottomNavigationBar` with 4 tabs: Home, Catalogue, Cart, Orders
5. Implement `NavigationDrawer` with: user avatar/name, Profile, Settings, Logout
6. Wire drawer open/close state through a shared `DrawerState`
7. Add placeholder Composables for all screens (just show the route name)
8. Verify navigation between all tabs works on a device/emulator

**Relevant Context**
- Auth check: if user is not logged in and navigates to Cart/Orders/Checkout, redirect to Login with `popUpTo` so back press returns to the attempted destination
- Use `rememberNavController()` + `BackHandler` for drawer close-on-back

---

### Sub-Task 6 — Authentication Screens (Login & Register)
**Status:** `[ ] pending`

**Intent**  
Build Login and Register screens with form validation, backed by `AuthViewModel`, wiring `LoginUseCase` and `RegisterUseCase`.

**Expected Outcomes**
- Login screen: email + password fields, "Login" button, "Register" link
- Register screen: name, email, password, confirm-password fields
- Form validation errors shown inline (empty fields, invalid email, password mismatch)
- Successful login navigates to Home and clears the back stack
- Guest users can continue browsing; login is prompted on Cart access
- Logged-in state persisted via `DataStore` or Room user session flag

**Todo List**
1. Create `AuthViewModel` with `loginState: StateFlow<AuthState>` and `registerState`
2. Implement `LoginScreen.kt` composable with:
   - Email + Password `OutlinedTextField`s
   - Show/hide password toggle
   - Error messages below fields
   - Progress indicator during login
   - "Continue as Guest" button
3. Implement `RegisterScreen.kt` composable with name, email, password, confirm-password
4. Add `DataStore` (or Room flag) to persist logged-in user id across sessions
5. Unit test `AuthViewModel`: login success, login failure (wrong password), register validation

**Relevant Context**
- Seed users: `alice@bookstore.com / password123`, `bob@bookstore.com / password123`
- `AuthRepository.login()` hashes password and compares with stored hash
- On "Continue as Guest" set a `guestMode = true` flag; this is checked at checkout

---

### Sub-Task 7 — Home Screen
**Status:** `[ ] pending`

**Intent**  
Build the Home screen showing: featured/banner books, category quick-access chips, recommended books (based on order history), and a recent orders "Buy Again" strip.

**Expected Outcomes**
- Hero banner carousel with 3–4 featured books
- Horizontal category chip row (Fiction, Science, etc.) each navigating to Catalogue filtered by category
- "Recommended for You" horizontal scroll (from `GetRecommendedBooksUseCase`)
- "Buy Again" strip showing last 3 ordered books
- Top app bar with search icon and drawer hamburger

**Todo List**
1. Create `HomeViewModel` consuming `GetFeaturedBooksUseCase`, `GetCategoriesUseCase`, `GetRecommendedBooksUseCase`, `GetOrderHistoryUseCase`
2. Implement `HomeScreen.kt` with:
   - `TopAppBar` with search icon + drawer open button
   - `AutoScrollingBanner` (LazyRow with auto-scroll timer) for featured books
   - `CategoryChipRow` — horizontal `LazyRow` of pill chips
   - `RecommendedSection` — horizontal book card scroll
   - `BuyAgainSection` — horizontal strip of past-order books with "Add to Cart" button
3. Create reusable `BookCard` composable (cover image, title, author, price, delivery date badge)
4. Unit test `HomeViewModel`: featured books loaded, recommendations derived from order history

**Relevant Context**
- `GetRecommendedBooksUseCase` filters books by categories appearing in the user's order history that the user has not already purchased
- Guest users see recommendations placeholder ("Login to see personalized picks")

---

### Sub-Task 8 — Catalogue Screen (Categories, Brand Browse, Product List)
**Status:** `[ ] pending`

**Intent**  
Build the Catalogue flow: Category List → Product List (filterable by brand) → Product Detail with related products.

**Expected Outcomes**
- Category grid screen with icon + name per category
- Product list screen with brand filter chips at the top
- Each product card shows cover, title, author, price, tentative delivery date badge
- Product detail screen: full description, price, delivery date, brand, rating, "Add to Cart" CTA, related products horizontal scroll
- Search bar accessible from both Catalogue and Home

**Todo List**
1. Create `CatalogueViewModel` (categories + selected category state)
2. Create `ProductListViewModel` (books for category, brand filter state)
3. Create `ProductDetailViewModel` (book detail + related books + add-to-cart action)
4. Implement `CatalogueScreen.kt` — `LazyVerticalGrid` of category cards
5. Implement `ProductListScreen.kt` — brand filter chips + `LazyVerticalGrid` of `BookCard`s
6. Implement `ProductDetailScreen.kt`:
   - Cover image (full width hero)
   - Title, author, brand, rating stars, price
   - "Est. Delivery: {date}" badge
   - Quantity selector (+/-)
   - "Add to Cart" button (triggers `AddToCartUseCase`)
   - "Related Books" horizontal scroll at bottom
7. Unit test `ProductDetailViewModel`: add to cart, related books loading

**Relevant Context**
- `deliveryDate` on `Book` is a pre-computed date string seeded in Room (e.g., "+3-5 business days")
- Brand filter is derived from the book list — extract unique `book.brand` values
- Related books = same category, exclude current book, limit 5

---

### Sub-Task 9 — Shopping Cart Screen
**Status:** `[ ] pending`

**Intent**  
Build the Cart screen showing all cart items, quantity controls, totals, and an order-history-based "You may also like" recommendation strip.

**Expected Outcomes**
- List of cart items: cover, title, price, quantity stepper, remove button
- Subtotal, estimated delivery fee, total
- "You May Also Like" horizontal book strip (from `GetRecommendedBooksUseCase`)
- "Proceed to Checkout" button — prompts login if guest
- Empty cart state with CTA to browse

**Todo List**
1. Create `CartViewModel` consuming `GetCartUseCase`, `RemoveFromCartUseCase`, `UpdateCartQuantityUseCase`, `GetRecommendedBooksUseCase`
2. Implement `CartScreen.kt`:
   - `LazyColumn` of `CartItemRow` (swipe-to-delete + quantity stepper)
   - Order summary card (subtotal, delivery, total)
   - "You May Also Like" `LazyRow`
   - "Proceed to Checkout" button with login gate
3. Unit test `CartViewModel`: add/remove/update quantity, total calculation

**Relevant Context**
- Cart items are stored per `userId`; guest cart uses `userId = -1` and is merged on login
- Delivery fee: flat ₹49 if total < ₹500, else free (mock rule)

---

### Sub-Task 10 — Checkout Flow (Address → Payment → Confirmation)
**Status:** `[ ] pending`

**Intent**  
Build the 3-step checkout flow as a nested nav graph: (1) Address Selection, (2) Payment, (3) Order Confirmation.

**Expected Outcomes**
- **Address Screen**: list of saved addresses + "Add New Address" form; user selects one
- **Payment Screen**: credit card form (mocked, no real validation) + gift points toggle showing available balance and discount
- **Confirmation Screen**: order summary card, order ID, estimated delivery, "Continue Shopping" + "View Orders" CTAs
- Order cancellation button visible on confirmation if within 48h

**Todo List**
1. Create `CheckoutViewModel` holding address selection, payment state, order placement state as a single shared VM across the 3 screens
2. Implement `AddressScreen.kt`:
   - Radio-select list of saved addresses
   - "Add New" bottom sheet with street, city, pincode fields
   - "Continue to Payment" button
3. Implement `PaymentScreen.kt`:
   - Order summary (read-only)
   - Credit card section: card number (masked), expiry, CVV mock fields
   - Gift Points toggle: "Use 200 points = ₹20 off" — deducted from total
   - "Pay Now" button triggers `ProcessPaymentUseCase` (mock: always succeeds after 1.5s delay)
   - Loading state during payment
4. Implement `ConfirmationScreen.kt`:
   - Success animation (Compose animated checkmark)
   - Order ID, items summary, total paid, delivery address, estimated delivery
   - "Cancel Order" button (shown only if `canCancel = true`)
   - "Continue Shopping" navigates to Home, clearing checkout back stack
5. Unit test `CheckoutViewModel`: gift points calculation, address selection, order placement

**Relevant Context**
- `ProcessPaymentUseCase` is a mock — delay 1.5s, return `PaymentResult(success=true, ...)`
- `RedeemGiftPointsUseCase` calculates discount: 1 point = ₹0.10; min redemption 100 points
- After successful order: `clearCart()`, deduct points from user balance, insert new Order record

---

### Sub-Task 11 — Order History Screen
**Status:** `[ ] pending`

**Intent**  
Build the Order History screen listing all past orders with status, and "Buy Again" action per order.

**Expected Outcomes**
- Ordered list of past orders (newest first): order ID, date, item count, total, status badge
- Expandable order row showing individual book items
- "Buy Again" button on each order re-adds all items to cart via `BuyAgainUseCase`
- "Cancel Order" button visible when `canCancel = true` (within 48h)

**Todo List**
1. Create `OrderHistoryViewModel` consuming `GetOrderHistoryUseCase`, `BuyAgainUseCase`, `CancelOrderUseCase`
2. Implement `OrderHistoryScreen.kt`:
   - `LazyColumn` of expandable `OrderCard`s
   - `OrderCard`: order header (ID, date, total, status chip) + collapsible items list
   - "Buy Again" button calls `BuyAgainUseCase`, shows snackbar "Items added to cart"
   - "Cancel Order" button calls `CancelOrderUseCase`, shows confirmation dialog
3. Unit test `OrderHistoryViewModel`: cancel within 48h allowed, cancel after 48h blocked, buy-again adds to cart

**Relevant Context**
- `CancelOrderUseCase` returns `Result.failure` if `now - placedAt > 48 * 3600 * 1000`
- Seed data: one order per user is < 48h old (use `System.currentTimeMillis() - 10 * 3600 * 1000`), one is older
- Cancelled orders show status `CANCELLED` and hide the cancel button

---

### Sub-Task 12 — Profile Screen & Navigation Drawer Content
**Status:** `[ ] pending`

**Intent**  
Build the drawer content (profile header, nav items) and a Profile screen showing account details, saved addresses, and gift point balance.

**Expected Outcomes**
- Drawer header: avatar initials circle, user name, email
- Drawer items: Profile, Order History (shortcut), Settings (placeholder), Logout
- Profile screen: name, email (read-only), gift point balance, saved addresses list
- Logout clears session and navigates to Login

**Todo List**
1. Create `ProfileViewModel` consuming `GetAddressesUseCase`, `GetOrderHistoryUseCase` (for points)
2. Implement `DrawerContent.kt` composable
3. Implement `ProfileScreen.kt` with account info card, points balance chip, addresses list
4. Wire Logout to `LogoutUseCase` + navigate to Login with `popUpTo(0)`

**Relevant Context**
- Gift points balance comes from `User.giftPoints` — updated after redemption at checkout

---

### Sub-Task 13 — Unit Tests
**Status:** `[ ] pending`

**Intent**  
Write comprehensive unit tests for all ViewModels and key use cases to ensure business logic is correct and regressions are caught.

**Expected Outcomes**
- All ViewModel tests pass: `AuthViewModel`, `HomeViewModel`, `CartViewModel`, `CheckoutViewModel`, `OrderHistoryViewModel`, `ProductDetailViewModel`
- Use-case tests: `CancelOrderUseCase`, `GetRecommendedBooksUseCase`, `RedeemGiftPointsUseCase`, `BuyAgainUseCase`
- `./gradlew test` passes with no failures

**Todo List**
1. Set up test dependencies: MockK, Turbine, `kotlinx-coroutines-test`, `TestDispatcher`
2. Create `AuthViewModelTest`: login success, wrong password, register validation errors
3. Create `CartViewModelTest`: add item, remove item, quantity update, total calculation with/without delivery fee
4. Create `CheckoutViewModelTest`: gift point deduction, address selection, order placement
5. Create `OrderHistoryViewModelTest`: cancel within 48h, cancel after 48h, buy-again
6. Create `CancelOrderUseCaseTest`: time boundary conditions
7. Create `GetRecommendedBooksUseCaseTest`: empty history, repeat categories, excluded purchased books
8. Create `RedeemGiftPointsUseCaseTest`: minimum threshold, max redemption (can't exceed total)

**Relevant Context**
- Use `TestCoroutineDispatcher` / `UnconfinedTestDispatcher` for ViewModel tests
- Mock all repositories using MockK `every { } returns flow { ... }`
- Turbine's `test { }` block for asserting Flow emissions

---

### Sub-Task 14 — UI Polish & Final Integration Verification
**Status:** `[ ] pending`

**Intent**  
Apply consistent Material 3 theming, fix any integration issues discovered when all screens are wired together, and run a final build + test pass.

**Expected Outcomes**
- App-wide `MaterialTheme` with brand colors (book-store warm palette)
- All screens use consistent typography, spacing, elevation
- Empty states and loading skeletons for all async screens
- `./gradlew assembleDebug` succeeds with no warnings
- `./gradlew test` passes

**Todo List**
1. Define `BookStoreTheme` in `ui/theme/`: primary (warm amber), secondary (dark navy), custom typography
2. Add `LoadingShimmer` placeholder composable for book cards
3. Add empty-state composables (image + message + CTA) for: Cart, Orders, Catalogue
4. Review all screens for consistent padding (16dp horizontal), corner radius (12dp cards)
5. Run `./gradlew assembleDebug` — fix any compilation issues
6. Run `./gradlew test` — fix any failing tests
7. Smoke-test full user journey: Login → Browse → Add to Cart → Checkout → Confirm → Order History → Cancel

**Relevant Context**
- Material 3 `dynamicColorScheme` can be offered as an optional toggle
- Target: no Lint errors in the `app` module for new code

---

## Data Source Swap Guide (Room → API)

When the backend is ready, swapping data sources requires only these changes:

1. **Implement `RemoteDataSource`** classes using Retrofit (they already exist as stubs)
2. **In Hilt `DataSourceModule`**, change the `@Binds` annotation to point to `RemoteDataSource` instead of `LocalDataSource`
3. **No changes** needed in domain layer, use cases, ViewModels, or UI

This is enforced by the `DataSource` interface boundary between `data/repository` and `data/local` / `data/remote`.

---

## Dependency Graph

```
UI (Composables) → ViewModel → UseCases → RepositoryInterface ← RepositoryImpl → DataSourceInterface ← LocalDataSource (Room)
                                                                                                       ← RemoteDataSource (Retrofit, future)
```
