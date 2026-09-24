package com.bookstore.data.repository;

import com.bookstore.data.local.dao.BookDao;
import com.bookstore.data.local.dao.CartDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CartRepositoryImpl_Factory implements Factory<CartRepositoryImpl> {
  private final Provider<CartDao> cartDaoProvider;

  private final Provider<BookDao> bookDaoProvider;

  public CartRepositoryImpl_Factory(Provider<CartDao> cartDaoProvider,
      Provider<BookDao> bookDaoProvider) {
    this.cartDaoProvider = cartDaoProvider;
    this.bookDaoProvider = bookDaoProvider;
  }

  @Override
  public CartRepositoryImpl get() {
    return newInstance(cartDaoProvider.get(), bookDaoProvider.get());
  }

  public static CartRepositoryImpl_Factory create(Provider<CartDao> cartDaoProvider,
      Provider<BookDao> bookDaoProvider) {
    return new CartRepositoryImpl_Factory(cartDaoProvider, bookDaoProvider);
  }

  public static CartRepositoryImpl newInstance(CartDao cartDao, BookDao bookDao) {
    return new CartRepositoryImpl(cartDao, bookDao);
  }
}
