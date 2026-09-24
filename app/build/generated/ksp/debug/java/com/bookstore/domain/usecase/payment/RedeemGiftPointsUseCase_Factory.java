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
public final class RedeemGiftPointsUseCase_Factory implements Factory<RedeemGiftPointsUseCase> {
  @Override
  public RedeemGiftPointsUseCase get() {
    return newInstance();
  }

  public static RedeemGiftPointsUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RedeemGiftPointsUseCase newInstance() {
    return new RedeemGiftPointsUseCase();
  }

  private static final class InstanceHolder {
    private static final RedeemGiftPointsUseCase_Factory INSTANCE = new RedeemGiftPointsUseCase_Factory();
  }
}
