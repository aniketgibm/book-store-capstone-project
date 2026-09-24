package com.bookstore.ui.navigation;

import com.bookstore.data.local.SessionDataStore;
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
public final class SessionViewModel_Factory implements Factory<SessionViewModel> {
  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public SessionViewModel_Factory(Provider<SessionDataStore> sessionDataStoreProvider) {
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public SessionViewModel get() {
    return newInstance(sessionDataStoreProvider.get());
  }

  public static SessionViewModel_Factory create(
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new SessionViewModel_Factory(sessionDataStoreProvider);
  }

  public static SessionViewModel newInstance(SessionDataStore sessionDataStore) {
    return new SessionViewModel(sessionDataStore);
  }
}
