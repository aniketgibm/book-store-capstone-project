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
public final class AddAddressUseCase_Factory implements Factory<AddAddressUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public AddAddressUseCase_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public AddAddressUseCase get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static AddAddressUseCase_Factory create(Provider<UserRepository> userRepositoryProvider) {
    return new AddAddressUseCase_Factory(userRepositoryProvider);
  }

  public static AddAddressUseCase newInstance(UserRepository userRepository) {
    return new AddAddressUseCase(userRepository);
  }
}
