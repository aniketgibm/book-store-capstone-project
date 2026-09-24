package com.bookstore.data.repository;

import com.bookstore.data.local.dao.AddressDao;
import com.bookstore.data.local.dao.UserDao;
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
public final class UserRepositoryImpl_Factory implements Factory<UserRepositoryImpl> {
  private final Provider<UserDao> userDaoProvider;

  private final Provider<AddressDao> addressDaoProvider;

  public UserRepositoryImpl_Factory(Provider<UserDao> userDaoProvider,
      Provider<AddressDao> addressDaoProvider) {
    this.userDaoProvider = userDaoProvider;
    this.addressDaoProvider = addressDaoProvider;
  }

  @Override
  public UserRepositoryImpl get() {
    return newInstance(userDaoProvider.get(), addressDaoProvider.get());
  }

  public static UserRepositoryImpl_Factory create(Provider<UserDao> userDaoProvider,
      Provider<AddressDao> addressDaoProvider) {
    return new UserRepositoryImpl_Factory(userDaoProvider, addressDaoProvider);
  }

  public static UserRepositoryImpl newInstance(UserDao userDao, AddressDao addressDao) {
    return new UserRepositoryImpl(userDao, addressDao);
  }
}
