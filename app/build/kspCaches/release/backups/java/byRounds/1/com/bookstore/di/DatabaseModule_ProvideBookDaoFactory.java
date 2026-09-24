package com.bookstore.di;

import com.bookstore.data.local.BookStoreDatabase;
import com.bookstore.data.local.dao.BookDao;
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
public final class DatabaseModule_ProvideBookDaoFactory implements Factory<BookDao> {
  private final Provider<BookStoreDatabase> dbProvider;

  public DatabaseModule_ProvideBookDaoFactory(Provider<BookStoreDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BookDao get() {
    return provideBookDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBookDaoFactory create(
      Provider<BookStoreDatabase> dbProvider) {
    return new DatabaseModule_ProvideBookDaoFactory(dbProvider);
  }

  public static BookDao provideBookDao(BookStoreDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBookDao(db));
  }
}
