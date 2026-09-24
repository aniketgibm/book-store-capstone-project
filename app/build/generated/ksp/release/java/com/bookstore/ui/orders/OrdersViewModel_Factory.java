package com.bookstore.ui.orders;

import com.bookstore.data.local.SessionDataStore;
import com.bookstore.domain.usecase.order.BuyAgainUseCase;
import com.bookstore.domain.usecase.order.CancelOrderUseCase;
import com.bookstore.domain.usecase.order.GetOrderHistoryUseCase;
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
public final class OrdersViewModel_Factory implements Factory<OrdersViewModel> {
  private final Provider<GetOrderHistoryUseCase> getOrderHistoryUseCaseProvider;

  private final Provider<CancelOrderUseCase> cancelOrderUseCaseProvider;

  private final Provider<BuyAgainUseCase> buyAgainUseCaseProvider;

  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public OrdersViewModel_Factory(Provider<GetOrderHistoryUseCase> getOrderHistoryUseCaseProvider,
      Provider<CancelOrderUseCase> cancelOrderUseCaseProvider,
      Provider<BuyAgainUseCase> buyAgainUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    this.getOrderHistoryUseCaseProvider = getOrderHistoryUseCaseProvider;
    this.cancelOrderUseCaseProvider = cancelOrderUseCaseProvider;
    this.buyAgainUseCaseProvider = buyAgainUseCaseProvider;
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public OrdersViewModel get() {
    return newInstance(getOrderHistoryUseCaseProvider.get(), cancelOrderUseCaseProvider.get(), buyAgainUseCaseProvider.get(), sessionDataStoreProvider.get());
  }

  public static OrdersViewModel_Factory create(
      Provider<GetOrderHistoryUseCase> getOrderHistoryUseCaseProvider,
      Provider<CancelOrderUseCase> cancelOrderUseCaseProvider,
      Provider<BuyAgainUseCase> buyAgainUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new OrdersViewModel_Factory(getOrderHistoryUseCaseProvider, cancelOrderUseCaseProvider, buyAgainUseCaseProvider, sessionDataStoreProvider);
  }

  public static OrdersViewModel newInstance(GetOrderHistoryUseCase getOrderHistoryUseCase,
      CancelOrderUseCase cancelOrderUseCase, BuyAgainUseCase buyAgainUseCase,
      SessionDataStore sessionDataStore) {
    return new OrdersViewModel(getOrderHistoryUseCase, cancelOrderUseCase, buyAgainUseCase, sessionDataStore);
  }
}
