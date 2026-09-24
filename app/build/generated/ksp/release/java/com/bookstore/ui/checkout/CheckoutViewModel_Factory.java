package com.bookstore.ui.checkout;

import com.bookstore.data.local.SessionDataStore;
import com.bookstore.domain.repository.UserRepository;
import com.bookstore.domain.usecase.cart.GetCartUseCase;
import com.bookstore.domain.usecase.order.PlaceOrderUseCase;
import com.bookstore.domain.usecase.payment.ProcessPaymentUseCase;
import com.bookstore.domain.usecase.payment.RedeemGiftPointsUseCase;
import com.bookstore.domain.usecase.user.AddAddressUseCase;
import com.bookstore.domain.usecase.user.GetAddressesUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class CheckoutViewModel_Factory implements Factory<CheckoutViewModel> {
  private final Provider<GetCartUseCase> getCartUseCaseProvider;

  private final Provider<GetAddressesUseCase> getAddressesUseCaseProvider;

  private final Provider<AddAddressUseCase> addAddressUseCaseProvider;

  private final Provider<ProcessPaymentUseCase> processPaymentUseCaseProvider;

  private final Provider<PlaceOrderUseCase> placeOrderUseCaseProvider;

  private final Provider<RedeemGiftPointsUseCase> redeemGiftPointsUseCaseProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public CheckoutViewModel_Factory(Provider<GetCartUseCase> getCartUseCaseProvider,
      Provider<GetAddressesUseCase> getAddressesUseCaseProvider,
      Provider<AddAddressUseCase> addAddressUseCaseProvider,
      Provider<ProcessPaymentUseCase> processPaymentUseCaseProvider,
      Provider<PlaceOrderUseCase> placeOrderUseCaseProvider,
      Provider<RedeemGiftPointsUseCase> redeemGiftPointsUseCaseProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    this.getCartUseCaseProvider = getCartUseCaseProvider;
    this.getAddressesUseCaseProvider = getAddressesUseCaseProvider;
    this.addAddressUseCaseProvider = addAddressUseCaseProvider;
    this.processPaymentUseCaseProvider = processPaymentUseCaseProvider;
    this.placeOrderUseCaseProvider = placeOrderUseCaseProvider;
    this.redeemGiftPointsUseCaseProvider = redeemGiftPointsUseCaseProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public CheckoutViewModel get() {
    return newInstance(getCartUseCaseProvider.get(), getAddressesUseCaseProvider.get(), addAddressUseCaseProvider.get(), processPaymentUseCaseProvider.get(), placeOrderUseCaseProvider.get(), redeemGiftPointsUseCaseProvider.get(), userRepositoryProvider.get(), sessionDataStoreProvider.get());
  }

  public static CheckoutViewModel_Factory create(Provider<GetCartUseCase> getCartUseCaseProvider,
      Provider<GetAddressesUseCase> getAddressesUseCaseProvider,
      Provider<AddAddressUseCase> addAddressUseCaseProvider,
      Provider<ProcessPaymentUseCase> processPaymentUseCaseProvider,
      Provider<PlaceOrderUseCase> placeOrderUseCaseProvider,
      Provider<RedeemGiftPointsUseCase> redeemGiftPointsUseCaseProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new CheckoutViewModel_Factory(getCartUseCaseProvider, getAddressesUseCaseProvider, addAddressUseCaseProvider, processPaymentUseCaseProvider, placeOrderUseCaseProvider, redeemGiftPointsUseCaseProvider, userRepositoryProvider, sessionDataStoreProvider);
  }

  public static CheckoutViewModel newInstance(GetCartUseCase getCartUseCase,
      GetAddressesUseCase getAddressesUseCase, AddAddressUseCase addAddressUseCase,
      ProcessPaymentUseCase processPaymentUseCase, PlaceOrderUseCase placeOrderUseCase,
      RedeemGiftPointsUseCase redeemGiftPointsUseCase, UserRepository userRepository,
      SessionDataStore sessionDataStore) {
    return new CheckoutViewModel(getCartUseCase, getAddressesUseCase, addAddressUseCase, processPaymentUseCase, placeOrderUseCase, redeemGiftPointsUseCase, userRepository, sessionDataStore);
  }
}
