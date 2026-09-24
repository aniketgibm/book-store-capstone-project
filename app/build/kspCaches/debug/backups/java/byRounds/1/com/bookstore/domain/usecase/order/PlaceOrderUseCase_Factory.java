package com.bookstore.domain.usecase.order;

import com.bookstore.domain.repository.CartRepository;
import com.bookstore.domain.repository.OrderRepository;
import com.bookstore.domain.repository.UserRepository;
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
public final class PlaceOrderUseCase_Factory implements Factory<PlaceOrderUseCase> {
  private final Provider<OrderRepository> orderRepositoryProvider;

  private final Provider<CartRepository> cartRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public PlaceOrderUseCase_Factory(Provider<OrderRepository> orderRepositoryProvider,
      Provider<CartRepository> cartRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.orderRepositoryProvider = orderRepositoryProvider;
    this.cartRepositoryProvider = cartRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public PlaceOrderUseCase get() {
    return newInstance(orderRepositoryProvider.get(), cartRepositoryProvider.get(), userRepositoryProvider.get());
  }

  public static PlaceOrderUseCase_Factory create(Provider<OrderRepository> orderRepositoryProvider,
      Provider<CartRepository> cartRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new PlaceOrderUseCase_Factory(orderRepositoryProvider, cartRepositoryProvider, userRepositoryProvider);
  }

  public static PlaceOrderUseCase newInstance(OrderRepository orderRepository,
      CartRepository cartRepository, UserRepository userRepository) {
    return new PlaceOrderUseCase(orderRepository, cartRepository, userRepository);
  }
}
