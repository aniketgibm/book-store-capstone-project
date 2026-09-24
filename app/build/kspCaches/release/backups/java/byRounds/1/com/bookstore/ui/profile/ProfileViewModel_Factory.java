package com.bookstore.ui.profile;

import com.bookstore.data.local.SessionDataStore;
import com.bookstore.domain.repository.UserRepository;
import com.bookstore.domain.usecase.auth.LogoutUseCase;
import com.bookstore.domain.usecase.user.GetAddressesUseCase;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<LogoutUseCase> logoutUseCaseProvider;

  private final Provider<GetAddressesUseCase> getAddressesUseCaseProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public ProfileViewModel_Factory(Provider<LogoutUseCase> logoutUseCaseProvider,
      Provider<GetAddressesUseCase> getAddressesUseCaseProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    this.logoutUseCaseProvider = logoutUseCaseProvider;
    this.getAddressesUseCaseProvider = getAddressesUseCaseProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(logoutUseCaseProvider.get(), getAddressesUseCaseProvider.get(), userRepositoryProvider.get(), sessionDataStoreProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<LogoutUseCase> logoutUseCaseProvider,
      Provider<GetAddressesUseCase> getAddressesUseCaseProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new ProfileViewModel_Factory(logoutUseCaseProvider, getAddressesUseCaseProvider, userRepositoryProvider, sessionDataStoreProvider);
  }

  public static ProfileViewModel newInstance(LogoutUseCase logoutUseCase,
      GetAddressesUseCase getAddressesUseCase, UserRepository userRepository,
      SessionDataStore sessionDataStore) {
    return new ProfileViewModel(logoutUseCase, getAddressesUseCase, userRepository, sessionDataStore);
  }
}
