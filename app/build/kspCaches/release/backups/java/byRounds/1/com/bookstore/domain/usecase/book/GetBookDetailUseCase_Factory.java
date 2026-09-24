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
public final class GetBookDetailUseCase_Factory implements Factory<GetBookDetailUseCase> {
  private final Provider<BookRepository> bookRepositoryProvider;

  public GetBookDetailUseCase_Factory(Provider<BookRepository> bookRepositoryProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
  }

  @Override
  public GetBookDetailUseCase get() {
    return newInstance(bookRepositoryProvider.get());
  }

  public static GetBookDetailUseCase_Factory create(
      Provider<BookRepository> bookRepositoryProvider) {
    return new GetBookDetailUseCase_Factory(bookRepositoryProvider);
  }

  public static GetBookDetailUseCase newInstance(BookRepository bookRepository) {
    return new GetBookDetailUseCase(bookRepository);
  }
}
