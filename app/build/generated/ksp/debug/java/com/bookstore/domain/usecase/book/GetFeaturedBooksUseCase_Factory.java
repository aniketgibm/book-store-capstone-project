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
public final class GetFeaturedBooksUseCase_Factory implements Factory<GetFeaturedBooksUseCase> {
  private final Provider<BookRepository> bookRepositoryProvider;

  public GetFeaturedBooksUseCase_Factory(Provider<BookRepository> bookRepositoryProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
  }

  @Override
  public GetFeaturedBooksUseCase get() {
    return newInstance(bookRepositoryProvider.get());
  }

  public static GetFeaturedBooksUseCase_Factory create(
      Provider<BookRepository> bookRepositoryProvider) {
    return new GetFeaturedBooksUseCase_Factory(bookRepositoryProvider);
  }

  public static GetFeaturedBooksUseCase newInstance(BookRepository bookRepository) {
    return new GetFeaturedBooksUseCase(bookRepository);
  }
}
