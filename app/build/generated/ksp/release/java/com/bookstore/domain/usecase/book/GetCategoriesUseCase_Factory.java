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
public final class GetCategoriesUseCase_Factory implements Factory<GetCategoriesUseCase> {
  private final Provider<BookRepository> bookRepositoryProvider;

  public GetCategoriesUseCase_Factory(Provider<BookRepository> bookRepositoryProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
  }

  @Override
  public GetCategoriesUseCase get() {
    return newInstance(bookRepositoryProvider.get());
  }

  public static GetCategoriesUseCase_Factory create(
      Provider<BookRepository> bookRepositoryProvider) {
    return new GetCategoriesUseCase_Factory(bookRepositoryProvider);
  }

  public static GetCategoriesUseCase newInstance(BookRepository bookRepository) {
    return new GetCategoriesUseCase(bookRepository);
  }
}
