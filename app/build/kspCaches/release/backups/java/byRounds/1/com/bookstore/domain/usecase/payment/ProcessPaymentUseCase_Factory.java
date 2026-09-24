package com.bookstore.domain.usecase.payment;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ProcessPaymentUseCase_Factory implements Factory<ProcessPaymentUseCase> {
  @Override
  public ProcessPaymentUseCase get() {
    return newInstance();
  }

  public static ProcessPaymentUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ProcessPaymentUseCase newInstance() {
    return new ProcessPaymentUseCase();
  }

  private static final class InstanceHolder {
    private static final ProcessPaymentUseCase_Factory INSTANCE = new ProcessPaymentUseCase_Factory();
  }
}
