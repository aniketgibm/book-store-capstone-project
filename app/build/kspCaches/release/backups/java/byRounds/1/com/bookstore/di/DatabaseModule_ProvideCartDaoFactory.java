package com.bookstore.di;

import com.bookstore.data.local.BookStoreDatabase;
import com.bookstore.data.local.dao.CartDao;
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
public final class DatabaseModule_ProvideCartDaoFactory implements Factory<CartDao> {
  private final Provider<BookStoreDatabase> dbProvider;

  public DatabaseModule_ProvideCartDaoFactory(Provider<BookStoreDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CartDao get() {
    return provideCartDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCartDaoFactory create(
      Provider<BookStoreDatabase> dbProvider) {
    return new DatabaseModule_ProvideCartDaoFactory(dbProvider);
  }

  public static CartDao provideCartDao(BookStoreDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCartDao(db));
  }
}
