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
public final class SearchBooksUseCase_Factory implements Factory<SearchBooksUseCase> {
  private final Provider<BookRepository> bookRepositoryProvider;

  public SearchBooksUseCase_Factory(Provider<BookRepository> bookRepositoryProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
  }

  @Override
  public SearchBooksUseCase get() {
    return newInstance(bookRepositoryProvider.get());
  }

  public static SearchBooksUseCase_Factory create(Provider<BookRepository> bookRepositoryProvider) {
    return new SearchBooksUseCase_Factory(bookRepositoryProvider);
  }

  public static SearchBooksUseCase newInstance(BookRepository bookRepository) {
    return new SearchBooksUseCase(bookRepository);
  }
}
