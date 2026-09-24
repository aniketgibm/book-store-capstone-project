package com.bookstore.data.repository;

import com.bookstore.data.local.SessionDataStore;
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
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<UserDao> userDaoProvider;

  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public AuthRepositoryImpl_Factory(Provider<UserDao> userDaoProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    this.userDaoProvider = userDaoProvider;
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(userDaoProvider.get(), sessionDataStoreProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<UserDao> userDaoProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new AuthRepositoryImpl_Factory(userDaoProvider, sessionDataStoreProvider);
  }

  public static AuthRepositoryImpl newInstance(UserDao userDao, SessionDataStore sessionDataStore) {
    return new AuthRepositoryImpl(userDao, sessionDataStore);
  }
}
