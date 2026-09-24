package com.bookstore.ui.auth;

import com.bookstore.data.local.SessionDataStore;
import com.bookstore.domain.usecase.auth.LoginUseCase;
import com.bookstore.domain.usecase.auth.LogoutUseCase;
import com.bookstore.domain.usecase.auth.RegisterUseCase;
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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<LoginUseCase> loginUseCaseProvider;

  private final Provider<RegisterUseCase> registerUseCaseProvider;

  private final Provider<LogoutUseCase> logoutUseCaseProvider;

  private final Provider<SessionDataStore> sessionDataStoreProvider;

  public AuthViewModel_Factory(Provider<LoginUseCase> loginUseCaseProvider,
      Provider<RegisterUseCase> registerUseCaseProvider,
      Provider<LogoutUseCase> logoutUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    this.loginUseCaseProvider = loginUseCaseProvider;
    this.registerUseCaseProvider = registerUseCaseProvider;
    this.logoutUseCaseProvider = logoutUseCaseProvider;
    this.sessionDataStoreProvider = sessionDataStoreProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(loginUseCaseProvider.get(), registerUseCaseProvider.get(), logoutUseCaseProvider.get(), sessionDataStoreProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<LoginUseCase> loginUseCaseProvider,
      Provider<RegisterUseCase> registerUseCaseProvider,
      Provider<LogoutUseCase> logoutUseCaseProvider,
      Provider<SessionDataStore> sessionDataStoreProvider) {
    return new AuthViewModel_Factory(loginUseCaseProvider, registerUseCaseProvider, logoutUseCaseProvider, sessionDataStoreProvider);
  }

  public static AuthViewModel newInstance(LoginUseCase loginUseCase,
      RegisterUseCase registerUseCase, LogoutUseCase logoutUseCase,
      SessionDataStore sessionDataStore) {
    return new AuthViewModel(loginUseCase, registerUseCase, logoutUseCase, sessionDataStore);
  }
}
