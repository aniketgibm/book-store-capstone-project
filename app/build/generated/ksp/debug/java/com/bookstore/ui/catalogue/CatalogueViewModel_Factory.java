package com.bookstore.ui.catalogue;

import com.bookstore.data.local.SessionDataStore;
import com.bookstore.domain.usecase.book.GetBookDetailUseCase;
import com.bookstore.domain.usecase.book.GetBooksByCategoryUseCase;
import com.bookstore.domain.usecase.book.GetCategoriesUseCase;
import com.bookstore.domain.usecase.book.GetRelatedBooksUseCase;
import com.bookstore.domain.usecase.book.SearchBooksUseCase;
import com.bookstore.domain.usecase.cart.AddToCartUseCase;
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
public final class CatalogueViewModel_Factory implements Factory<CatalogueViewModel> {
  private final Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider;

  private final Provider<GetBooksByCategoryUseCase> getBooksByCategoryUseCaseProvider;

  private final Provider<GetBookDetailUseCase> getBookDetailUseCaseProvider;

  private final Provider<GetRelatedBooksUseCase> getRelatedBooksUseCaseProvider;

  private final Provider<SearchBooksUseCase> searchBooksUseCaseProvider;

  private final Provider<AddToCartUseCase> addToCartUseCaseProvider;

  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public CatalogueViewModel_Factory(Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<GetBooksByCategoryUseCase> getBooksByCategoryUseCaseProvider,
      Provider<GetBookDetailUseCase> getBookDetailUseCaseProvider,
      Provider<GetRelatedBooksUseCase> getRelatedBooksUseCaseProvider,
      Provider<SearchBooksUseCase> searchBooksUseCaseProvider,
      Provider<AddToCartUseCase> addToCartUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    this.getCategoriesUseCaseProvider = getCategoriesUseCaseProvider;
    this.getBooksByCategoryUseCaseProvider = getBooksByCategoryUseCaseProvider;
    this.getBookDetailUseCaseProvider = getBookDetailUseCaseProvider;
    this.getRelatedBooksUseCaseProvider = getRelatedBooksUseCaseProvider;
    this.searchBooksUseCaseProvider = searchBooksUseCaseProvider;
    this.addToCartUseCaseProvider = addToCartUseCaseProvider;
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public CatalogueViewModel get() {
    return newInstance(getCategoriesUseCaseProvider.get(), getBooksByCategoryUseCaseProvider.get(), getBookDetailUseCaseProvider.get(), getRelatedBooksUseCaseProvider.get(), searchBooksUseCaseProvider.get(), addToCartUseCaseProvider.get(), sessionDataStoreProvider.get());
  }

  public static CatalogueViewModel_Factory create(
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<GetBooksByCategoryUseCase> getBooksByCategoryUseCaseProvider,
      Provider<GetBookDetailUseCase> getBookDetailUseCaseProvider,
      Provider<GetRelatedBooksUseCase> getRelatedBooksUseCaseProvider,
      Provider<SearchBooksUseCase> searchBooksUseCaseProvider,
      Provider<AddToCartUseCase> addToCartUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new CatalogueViewModel_Factory(getCategoriesUseCaseProvider, getBooksByCategoryUseCaseProvider, getBookDetailUseCaseProvider, getRelatedBooksUseCaseProvider, searchBooksUseCaseProvider, addToCartUseCaseProvider, sessionDataStoreProvider);
  }

  public static CatalogueViewModel newInstance(GetCategoriesUseCase getCategoriesUseCase,
      GetBooksByCategoryUseCase getBooksByCategoryUseCase,
      GetBookDetailUseCase getBookDetailUseCase, GetRelatedBooksUseCase getRelatedBooksUseCase,
      SearchBooksUseCase searchBooksUseCase, AddToCartUseCase addToCartUseCase,
      SessionDataStore sessionDataStore) {
    return new CatalogueViewModel(getCategoriesUseCase, getBooksByCategoryUseCase, getBookDetailUseCase, getRelatedBooksUseCase, searchBooksUseCase, addToCartUseCase, sessionDataStore);
  }
}
