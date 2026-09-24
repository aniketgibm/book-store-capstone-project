package com.bookstore.ui.cart;

import com.bookstore.data.local.SessionDataStore;
import com.bookstore.domain.usecase.book.GetRecommendedBooksUseCase;
import com.bookstore.domain.usecase.cart.AddToCartUseCase;
import com.bookstore.domain.usecase.cart.GetCartUseCase;
import com.bookstore.domain.usecase.cart.RemoveFromCartUseCase;
import com.bookstore.domain.usecase.cart.UpdateCartQuantityUseCase;
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
public final class CartViewModel_Factory implements Factory<CartViewModel> {
  private final Provider<GetCartUseCase> getCartUseCaseProvider;

  private final Provider<AddToCartUseCase> addToCartUseCaseProvider;

  private final Provider<RemoveFromCartUseCase> removeFromCartUseCaseProvider;

  private final Provider<UpdateCartQuantityUseCase> updateCartQuantityUseCaseProvider;

  private final Provider<GetRecommendedBooksUseCase> getRecommendedBooksUseCaseProvider;

  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public CartViewModel_Factory(Provider<GetCartUseCase> getCartUseCaseProvider,
      Provider<AddToCartUseCase> addToCartUseCaseProvider,
      Provider<RemoveFromCartUseCase> removeFromCartUseCaseProvider,
      Provider<UpdateCartQuantityUseCase> updateCartQuantityUseCaseProvider,
      Provider<GetRecommendedBooksUseCase> getRecommendedBooksUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    this.getCartUseCaseProvider = getCartUseCaseProvider;
    this.addToCartUseCaseProvider = addToCartUseCaseProvider;
    this.removeFromCartUseCaseProvider = removeFromCartUseCaseProvider;
    this.updateCartQuantityUseCaseProvider = updateCartQuantityUseCaseProvider;
    this.getRecommendedBooksUseCaseProvider = getRecommendedBooksUseCaseProvider;
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public CartViewModel get() {
    return newInstance(getCartUseCaseProvider.get(), addToCartUseCaseProvider.get(), removeFromCartUseCaseProvider.get(), updateCartQuantityUseCaseProvider.get(), getRecommendedBooksUseCaseProvider.get(), sessionDataStoreProvider.get());
  }

  public static CartViewModel_Factory create(Provider<GetCartUseCase> getCartUseCaseProvider,
      Provider<AddToCartUseCase> addToCartUseCaseProvider,
      Provider<RemoveFromCartUseCase> removeFromCartUseCaseProvider,
      Provider<UpdateCartQuantityUseCase> updateCartQuantityUseCaseProvider,
      Provider<GetRecommendedBooksUseCase> getRecommendedBooksUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new CartViewModel_Factory(getCartUseCaseProvider, addToCartUseCaseProvider, removeFromCartUseCaseProvider, updateCartQuantityUseCaseProvider, getRecommendedBooksUseCaseProvider, sessionDataStoreProvider);
  }

  public static CartViewModel newInstance(GetCartUseCase getCartUseCase,
      AddToCartUseCase addToCartUseCase, RemoveFromCartUseCase removeFromCartUseCase,
      UpdateCartQuantityUseCase updateCartQuantityUseCase,
      GetRecommendedBooksUseCase getRecommendedBooksUseCase, SessionDataStore sessionDataStore) {
    return new CartViewModel(getCartUseCase, addToCartUseCase, removeFromCartUseCase, updateCartQuantityUseCase, getRecommendedBooksUseCase, sessionDataStore);
  }
}
