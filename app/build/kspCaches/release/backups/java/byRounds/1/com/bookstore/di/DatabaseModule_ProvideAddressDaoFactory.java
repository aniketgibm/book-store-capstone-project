package com.bookstore.di;

import com.bookstore.data.local.BookStoreDatabase;
import com.bookstore.data.local.dao.AddressDao;
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
public final class DatabaseModule_ProvideAddressDaoFactory implements Factory<AddressDao> {
  private final Provider<BookStoreDatabase> dbProvider;

  public DatabaseModule_ProvideAddressDaoFactory(Provider<BookStoreDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AddressDao get() {
    return provideAddressDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAddressDaoFactory create(
      Provider<BookStoreDatabase> dbProvider) {
    return new DatabaseModule_ProvideAddressDaoFactory(dbProvider);
  }

  public static AddressDao provideAddressDao(BookStoreDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAddressDao(db));
  }
}
