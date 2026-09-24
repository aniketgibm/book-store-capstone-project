package com.bookstore.domain.usecase.user;

import com.bookstore.domain.repository.UserRepository;
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
public final class GetAddressesUseCase_Factory implements Factory<GetAddressesUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public GetAddressesUseCase_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public GetAddressesUseCase get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static GetAddressesUseCase_Factory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new GetAddressesUseCase_Factory(userRepositoryProvider);
  }

  public static GetAddressesUseCase newInstance(UserRepository userRepository) {
    return new GetAddressesUseCase(userRepository);
  }
}
