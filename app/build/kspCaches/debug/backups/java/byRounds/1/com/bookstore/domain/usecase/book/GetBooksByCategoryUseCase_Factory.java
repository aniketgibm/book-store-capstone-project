package com.bookstore.domain.usecase.book;

import com.bookstore.domain.repository.BookRepository;
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
public final class GetBooksByCategoryUseCase_Factory implements Factory<GetBooksByCategoryUseCase> {
  private final Provider<BookRepository> bookRepositoryProvider;

  public GetBooksByCategoryUseCase_Factory(Provider<BookRepository> bookRepositoryProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
  }

  @Override
  public GetBooksByCategoryUseCase get() {
    return newInstance(bookRepositoryProvider.get());
  }

  public static GetBooksByCategoryUseCase_Factory create(
      Provider<BookRepository> bookRepositoryProvider) {
    return new GetBooksByCategoryUseCase_Factory(bookRepositoryProvider);
  }

  public static GetBooksByCategoryUseCase newInstance(BookRepository bookRepository) {
    return new GetBooksByCategoryUseCase(bookRepository);
  }
}
