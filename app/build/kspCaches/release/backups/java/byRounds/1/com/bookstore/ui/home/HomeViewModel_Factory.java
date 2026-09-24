package com.bookstore.ui.home;

import com.bookstore.data.local.SessionDataStore;
import com.bookstore.domain.usecase.book.GetCategoriesUseCase;
import com.bookstore.domain.usecase.book.GetFeaturedBooksUseCase;
import com.bookstore.domain.usecase.book.GetRecommendedBooksUseCase;
import com.bookstore.domain.usecase.cart.AddToCartUseCase;
import com.bookstore.domain.usecase.order.GetOrderHistoryUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<GetFeaturedBooksUseCase> getFeaturedBooksUseCaseProvider;

  private final Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider;

  private final Provider<GetRecommendedBooksUseCase> getRecommendedBooksUseCaseProvider;

  private final Provider<GetOrderHistoryUseCase> getOrderHistoryUseCaseProvider;

  private final Provider<AddToCartUseCase> addToCartUseCaseProvider;

  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public HomeViewModel_Factory(Provider<GetFeaturedBooksUseCase> getFeaturedBooksUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<GetRecommendedBooksUseCase> getRecommendedBooksUseCaseProvider,
      Provider<GetOrderHistoryUseCase> getOrderHistoryUseCaseProvider,
      Provider<AddToCartUseCase> addToCartUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    this.getFeaturedBooksUseCaseProvider = getFeaturedBooksUseCaseProvider;
    this.getCategoriesUseCaseProvider = getCategoriesUseCaseProvider;
    this.getRecommendedBooksUseCaseProvider = getRecommendedBooksUseCaseProvider;
    this.getOrderHistoryUseCaseProvider = getOrderHistoryUseCaseProvider;
    this.addToCartUseCaseProvider = addToCartUseCaseProvider;
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(getFeaturedBooksUseCaseProvider.get(), getCategoriesUseCaseProvider.get(), getRecommendedBooksUseCaseProvider.get(), getOrderHistoryUseCaseProvider.get(), addToCartUseCaseProvider.get(), sessionDataStoreProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<GetFeaturedBooksUseCase> getFeaturedBooksUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<GetRecommendedBooksUseCase> getRecommendedBooksUseCaseProvider,
      Provider<GetOrderHistoryUseCase> getOrderHistoryUseCaseProvider,
      Provider<AddToCartUseCase> addToCartUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new HomeViewModel_Factory(getFeaturedBooksUseCaseProvider, getCategoriesUseCaseProvider, getRecommendedBooksUseCaseProvider, getOrderHistoryUseCaseProvider, addToCartUseCaseProvider, sessionDataStoreProvider);
  }

  public static HomeViewModel newInstance(GetFeaturedBooksUseCase getFeaturedBooksUseCase,
      GetCategoriesUseCase getCategoriesUseCase,
      GetRecommendedBooksUseCase getRecommendedBooksUseCase,
      GetOrderHistoryUseCase getOrderHistoryUseCase, AddToCartUseCase addToCartUseCase,
      SessionDataStore sessionDataStore) {
    return new HomeViewModel(getFeaturedBooksUseCase, getCategoriesUseCase, getRecommendedBooksUseCase, getOrderHistoryUseCase, addToCartUseCase, sessionDataStore);
  }
}
