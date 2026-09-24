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
public final class GetOrderHistoryUseCase_Factory implements Factory<GetOrderHistoryUseCase> {
  private final Provider<OrderRepository> orderRepositoryProvider;

  public GetOrderHistoryUseCase_Factory(Provider<OrderRepository> orderRepositoryProvider) {
    this.orderRepositoryProvider = orderRepositoryProvider;
  }

  @Override
  public GetOrderHistoryUseCase get() {
    return newInstance(orderRepositoryProvider.get());
  }

  public static GetOrderHistoryUseCase_Factory create(
      Provider<OrderRepository> orderRepositoryProvider) {
    return new GetOrderHistoryUseCase_Factory(orderRepositoryProvider);
  }

  public static GetOrderHistoryUseCase newInstance(OrderRepository orderRepository) {
    return new GetOrderHistoryUseCase(orderRepository);
  }
}
