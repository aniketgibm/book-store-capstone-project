package com.bookstore.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookstore.data.local.SessionDataStore
import com.bookstore.domain.model.Address
import com.bookstore.domain.model.CartItem
import com.bookstore.domain.usecase.cart.GetCartUseCase
import com.bookstore.domain.usecase.order.PlaceOrderUseCase
import com.bookstore.domain.usecase.payment.ProcessPaymentUseCase
import com.bookstore.domain.usecase.payment.RedeemGiftPointsUseCase
import com.bookstore.domain.usecase.user.AddAddressUseCase
import com.bookstore.domain.usecase.user.GetAddressesUseCase
import com.bookstore.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CheckoutUiState(
    val cartItems: List<CartItem> = emptyList(),
    val addresses: List<Address> = emptyList(),
    val selectedAddress: Address? = null,
    val availableGiftPoints: Int = 0,
    val giftPointsToRedeem: Int = 0,
    val giftPointsDiscount: Double = 0.0,
    val subtotal: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val total: Double = 0.0,
    val cardNumber: String = "",
    val cardExpiry: String = "",
    val cardCvv: String = "",
    val isProcessingPayment: Boolean = false,
    val placedOrderId: Long? = null,
    val errorMessage: String? = null,
    val userId: Long = -1L,
    val isAddingAddress: Boolean = false
)

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val getAddressesUseCase: GetAddressesUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val processPaymentUseCase: ProcessPaymentUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val redeemGiftPointsUseCase: RedeemGiftPointsUseCase,
    private val userRepository: UserRepository,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    init {
        loadCheckoutData()
    }

    private fun loadCheckoutData() {
        viewModelScope.launch {
            val userId = sessionDataStore.currentUserId.first() ?: -1L
            _uiState.update { it.copy(userId = userId) }
            if (userId <= 0) return@launch

            // Load cart
            launch {
                getCartUseCase(userId).collect { items ->
                    val subtotal = items.sumOf { it.book.price * it.quantity }
                    val deliveryFee = if (subtotal > 500) 0.0 else if (items.isNotEmpty()) 49.0 else 0.0
                    _uiState.update { it.copy(cartItems = items, subtotal = subtotal, deliveryFee = deliveryFee, total = subtotal + deliveryFee - it.giftPointsDiscount) }
                }
            }

            // Load addresses
            launch {
                getAddressesUseCase(userId).collect { addresses ->
                    val defaultAddr = addresses.firstOrNull { it.isDefault } ?: addresses.firstOrNull()
                    _uiState.update { it.copy(addresses = addresses, selectedAddress = it.selectedAddress ?: defaultAddr) }
                }
            }

            // Load gift points
            val points = userRepository.getUserGiftPoints(userId)
            _uiState.update { it.copy(availableGiftPoints = points) }
        }
    }

    fun selectAddress(address: Address) = _uiState.update { it.copy(selectedAddress = address) }

    fun addNewAddress(address: Address) {
        viewModelScope.launch {
            val result = addAddressUseCase(address)
            if (result.isFailure) {
                _uiState.update { it.copy(errorMessage = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun updateCardNumber(value: String) = _uiState.update { it.copy(cardNumber = value) }
    fun updateCardExpiry(value: String) = _uiState.update { it.copy(cardExpiry = value) }
    fun updateCardCvv(value: String) = _uiState.update { it.copy(cardCvv = value) }

    fun toggleGiftPoints(enable: Boolean) {
        val state = _uiState.value
        if (!enable) {
            _uiState.update { it.copy(giftPointsToRedeem = 0, giftPointsDiscount = 0.0, total = it.subtotal + it.deliveryFee) }
            return
        }
        val result = redeemGiftPointsUseCase(state.availableGiftPoints, state.subtotal + state.deliveryFee, state.availableGiftPoints)
        result.onSuccess { redemption ->
            _uiState.update {
                it.copy(
                    giftPointsToRedeem = redemption.pointsToRedeem,
                    giftPointsDiscount = redemption.discountAmount,
                    total = it.subtotal + it.deliveryFee - redemption.discountAmount
                )
            }
        }
    }

    fun processPaymentAndPlaceOrder() {
        val state = _uiState.value
        if (state.selectedAddress == null) {
            _uiState.update { it.copy(errorMessage = "Please select a delivery address") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessingPayment = true, errorMessage = null) }
            try {
                val paymentResult = processPaymentUseCase(state.total, state.cardNumber, state.cardExpiry, state.cardCvv)
                if (paymentResult.success) {
                    val deliveryAddress = "${state.selectedAddress.fullAddress}, ${state.selectedAddress.city} - ${state.selectedAddress.pincode}"
                    val result = placeOrderUseCase(
                        userId = state.userId,
                        cartItems = state.cartItems,
                        deliveryAddress = deliveryAddress,
                        deliveryFee = state.deliveryFee,
                        discountAmount = state.giftPointsDiscount,
                        pointsRedeemed = state.giftPointsToRedeem
                    )
                    result.onSuccess { orderId ->
                        _uiState.update { it.copy(isProcessingPayment = false, placedOrderId = orderId) }
                    }
                    result.onFailure { e ->
                        _uiState.update { it.copy(isProcessingPayment = false, errorMessage = e.message) }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isProcessingPayment = false, errorMessage = e.message) }
            }
        }
    }

    fun clearError() = _uiState.update { it.copy(errorMessage = null) }
}
