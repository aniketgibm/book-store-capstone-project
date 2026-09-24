package com.bookstore.di;

import com.bookstore.data.local.BookStoreDatabase;
import com.bookstore.data.local.dao.OrderDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideOrderDaoFactory implements Factory<OrderDao> {
  private final Provider<BookStoreDatabase> dbProvider;

  public DatabaseModule_ProvideOrderDaoFactory(Provider<BookStoreDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public OrderDao get() {
    return provideOrderDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideOrderDaoFactory create(
      Provider<BookStoreDatabase> dbProvider) {
    return new DatabaseModule_ProvideOrderDaoFactory(dbProvider);
  }

  public static OrderDao provideOrderDao(BookStoreDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideOrderDao(db));
  }
}
