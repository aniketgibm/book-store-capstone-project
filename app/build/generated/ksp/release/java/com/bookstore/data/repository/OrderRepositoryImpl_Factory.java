package com.bookstore.data.repository;

import com.bookstore.data.local.dao.BookDao;
import com.bookstore.data.local.dao.OrderDao;
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
public final class OrderRepositoryImpl_Factory implements Factory<OrderRepositoryImpl> {
  private final Provider<OrderDao> orderDaoProvider;

  private final Provider<BookDao> bookDaoProvider;

  public OrderRepositoryImpl_Factory(Provider<OrderDao> orderDaoProvider,
      Provider<BookDao> bookDaoProvider) {
    this.orderDaoProvider = orderDaoProvider;
    this.bookDaoProvider = bookDaoProvider;
  }

  @Override
  public OrderRepositoryImpl get() {
    return newInstance(orderDaoProvider.get(), bookDaoProvider.get());
  }

  public static OrderRepositoryImpl_Factory create(Provider<OrderDao> orderDaoProvider,
      Provider<BookDao> bookDaoProvider) {
    return new OrderRepositoryImpl_Factory(orderDaoProvider, bookDaoProvider);
  }

  public static OrderRepositoryImpl newInstance(OrderDao orderDao, BookDao bookDao) {
    return new OrderRepositoryImpl(orderDao, bookDao);
  }
}
