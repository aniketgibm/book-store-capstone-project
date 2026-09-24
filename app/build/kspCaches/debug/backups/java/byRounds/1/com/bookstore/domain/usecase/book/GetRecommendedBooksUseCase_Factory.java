package com.bookstore.domain.usecase.book;

import com.bookstore.domain.repository.BookRepository;
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
public final class GetRecommendedBooksUseCase_Factory implements Factory<GetRecommendedBooksUseCase> {
  private final Provider<BookRepository> bookRepositoryProvider;

  private final Provider<OrderRepository> orderRepositoryProvider;

  public GetRecommendedBooksUseCase_Factory(Provider<BookRepository> bookRepositoryProvider,
      Provider<OrderRepository> orderRepositoryProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
    this.orderRepositoryProvider = orderRepositoryProvider;
  }

  @Override
  public GetRecommendedBooksUseCase get() {
    return newInstance(bookRepositoryProvider.get(), orderRepositoryProvider.get());
  }

  public static GetRecommendedBooksUseCase_Factory create(
      Provider<BookRepository> bookRepositoryProvider,
      Provider<OrderRepository> orderRepositoryProvider) {
    return new GetRecommendedBooksUseCase_Factory(bookRepositoryProvider, orderRepositoryProvider);
  }

  public static GetRecommendedBooksUseCase newInstance(BookRepository bookRepository,
      OrderRepository orderRepository) {
    return new GetRecommendedBooksUseCase(bookRepository, orderRepository);
  }
}
