package com.bookstore.domain.usecase.order;

import com.bookstore.domain.repository.CartRepository;
import com.bookstore.domain.repository.OrderRepository;
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
public final class BuyAgainUseCase_Factory implements Factory<BuyAgainUseCase> {
  private final Provider<OrderRepository> orderRepositoryProvider;

  private final Provider<CartRepository> cartRepositoryProvider;

  public BuyAgainUseCase_Factory(Provider<OrderRepository> orderRepositoryProvider,
      Provider<CartRepository> cartRepositoryProvider) {
    this.orderRepositoryProvider = orderRepositoryProvider;
    this.cartRepositoryProvider = cartRepositoryProvider;
  }

  @Override
  public BuyAgainUseCase get() {
    return newInstance(orderRepositoryProvider.get(), cartRepositoryProvider.get());
  }

  public static BuyAgainUseCase_Factory create(Provider<OrderRepository> orderRepositoryProvider,
      Provider<CartRepository> cartRepositoryProvider) {
    return new BuyAgainUseCase_Factory(orderRepositoryProvider, cartRepositoryProvider);
  }

  public static BuyAgainUseCase newInstance(OrderRepository orderRepository,
      CartRepository cartRepository) {
    return new BuyAgainUseCase(orderRepository, cartRepository);
  }
}
