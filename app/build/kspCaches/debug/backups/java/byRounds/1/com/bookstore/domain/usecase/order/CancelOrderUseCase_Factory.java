package com.bookstore.domain.usecase.order;

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
public final class CancelOrderUseCase_Factory implements Factory<CancelOrderUseCase> {
  private final Provider<OrderRepository> orderRepositoryProvider;

  public CancelOrderUseCase_Factory(Provider<OrderRepository> orderRepositoryProvider) {
    this.orderRepositoryProvider = orderRepositoryProvider;
  }

  @Override
  public CancelOrderUseCase get() {
    return newInstance(orderRepositoryProvider.get());
  }

  public static CancelOrderUseCase_Factory create(
      Provider<OrderRepository> orderRepositoryProvider) {
    return new CancelOrderUseCase_Factory(orderRepositoryProvider);
  }

  public static CancelOrderUseCase newInstance(OrderRepository orderRepository) {
    return new CancelOrderUseCase(orderRepository);
  }
}
