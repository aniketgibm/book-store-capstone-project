package com.bookstore;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.bookstore.data.local.BookStoreDatabase;
import com.bookstore.data.local.SessionDataStore;
import com.bookstore.data.local.dao.AddressDao;
import com.bookstore.data.local.dao.BookDao;
import com.bookstore.data.local.dao.CartDao;
import com.bookstore.data.local.dao.OrderDao;
import com.bookstore.data.local.dao.UserDao;
import com.bookstore.data.repository.AuthRepositoryImpl;
import com.bookstore.data.repository.BookRepositoryImpl;
import com.bookstore.data.repository.CartRepositoryImpl;
import com.bookstore.data.repository.OrderRepositoryImpl;
import com.bookstore.data.repository.UserRepositoryImpl;
import com.bookstore.di.DatabaseModule_ProvideAddressDaoFactory;
import com.bookstore.di.DatabaseModule_ProvideBookDaoFactory;
import com.bookstore.di.DatabaseModule_ProvideCartDaoFactory;
import com.bookstore.di.DatabaseModule_ProvideDatabaseFactory;
import com.bookstore.di.DatabaseModule_ProvideOrderDaoFactory;
import com.bookstore.di.DatabaseModule_ProvideUserDaoFactory;
import com.bookstore.domain.usecase.auth.LoginUseCase;
import com.bookstore.domain.usecase.auth.LogoutUseCase;
import com.bookstore.domain.usecase.auth.RegisterUseCase;
import com.bookstore.domain.usecase.book.GetBookDetailUseCase;
import com.bookstore.domain.usecase.book.GetBooksByCategoryUseCase;
import com.bookstore.domain.usecase.book.GetCategoriesUseCase;
import com.bookstore.domain.usecase.book.GetFeaturedBooksUseCase;
import com.bookstore.domain.usecase.book.GetRecommendedBooksUseCase;
import com.bookstore.domain.usecase.book.GetRelatedBooksUseCase;
import com.bookstore.domain.usecase.book.SearchBooksUseCase;
import com.bookstore.domain.usecase.cart.AddToCartUseCase;
import com.bookstore.domain.usecase.cart.GetCartUseCase;
import com.bookstore.domain.usecase.cart.RemoveFromCartUseCase;
import com.bookstore.domain.usecase.cart.UpdateCartQuantityUseCase;
import com.bookstore.domain.usecase.order.BuyAgainUseCase;
import com.bookstore.domain.usecase.order.CancelOrderUseCase;
import com.bookstore.domain.usecase.order.GetOrderHistoryUseCase;
import com.bookstore.domain.usecase.order.PlaceOrderUseCase;
import com.bookstore.domain.usecase.payment.ProcessPaymentUseCase;
import com.bookstore.domain.usecase.payment.RedeemGiftPointsUseCase;
import com.bookstore.domain.usecase.user.AddAddressUseCase;
import com.bookstore.domain.usecase.user.GetAddressesUseCase;
import com.bookstore.ui.auth.AuthViewModel;
import com.bookstore.ui.auth.AuthViewModel_HiltModules;
import com.bookstore.ui.cart.CartViewModel;
import com.bookstore.ui.cart.CartViewModel_HiltModules;
import com.bookstore.ui.catalogue.CatalogueViewModel;
import com.bookstore.ui.catalogue.CatalogueViewModel_HiltModules;
import com.bookstore.ui.checkout.CheckoutViewModel;
import com.bookstore.ui.checkout.CheckoutViewModel_HiltModules;
import com.bookstore.ui.home.HomeViewModel;
import com.bookstore.ui.home.HomeViewModel_HiltModules;
import com.bookstore.ui.navigation.SessionViewModel;
import com.bookstore.ui.navigation.SessionViewModel_HiltModules;
import com.bookstore.ui.orders.OrdersViewModel;
import com.bookstore.ui.orders.OrdersViewModel_HiltModules;
import com.bookstore.ui.profile.ProfileViewModel;
import com.bookstore.ui.profile.ProfileViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerBookStoreApp_HiltComponents_SingletonC {
  private DaggerBookStoreApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public BookStoreApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements BookStoreApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public BookStoreApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements BookStoreApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public BookStoreApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements BookStoreApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public BookStoreApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements BookStoreApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BookStoreApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements BookStoreApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BookStoreApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements BookStoreApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public BookStoreApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements BookStoreApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public BookStoreApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends BookStoreApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends BookStoreApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends BookStoreApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends BookStoreApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(8).put(LazyClassKeyProvider.com_bookstore_ui_auth_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_bookstore_ui_cart_CartViewModel, CartViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_bookstore_ui_catalogue_CatalogueViewModel, CatalogueViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_bookstore_ui_checkout_CheckoutViewModel, CheckoutViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_bookstore_ui_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_bookstore_ui_orders_OrdersViewModel, OrdersViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_bookstore_ui_profile_ProfileViewModel, ProfileViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_bookstore_ui_navigation_SessionViewModel, SessionViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_bookstore_ui_profile_ProfileViewModel = "com.bookstore.ui.profile.ProfileViewModel";

      static String com_bookstore_ui_cart_CartViewModel = "com.bookstore.ui.cart.CartViewModel";

      static String com_bookstore_ui_home_HomeViewModel = "com.bookstore.ui.home.HomeViewModel";

      static String com_bookstore_ui_catalogue_CatalogueViewModel = "com.bookstore.ui.catalogue.CatalogueViewModel";

      static String com_bookstore_ui_navigation_SessionViewModel = "com.bookstore.ui.navigation.SessionViewModel";

      static String com_bookstore_ui_orders_OrdersViewModel = "com.bookstore.ui.orders.OrdersViewModel";

      static String com_bookstore_ui_auth_AuthViewModel = "com.bookstore.ui.auth.AuthViewModel";

      static String com_bookstore_ui_checkout_CheckoutViewModel = "com.bookstore.ui.checkout.CheckoutViewModel";

      @KeepFieldType
      ProfileViewModel com_bookstore_ui_profile_ProfileViewModel2;

      @KeepFieldType
      CartViewModel com_bookstore_ui_cart_CartViewModel2;

      @KeepFieldType
      HomeViewModel com_bookstore_ui_home_HomeViewModel2;

      @KeepFieldType
      CatalogueViewModel com_bookstore_ui_catalogue_CatalogueViewModel2;

      @KeepFieldType
      SessionViewModel com_bookstore_ui_navigation_SessionViewModel2;

      @KeepFieldType
      OrdersViewModel com_bookstore_ui_orders_OrdersViewModel2;

      @KeepFieldType
      AuthViewModel com_bookstore_ui_auth_AuthViewModel2;

      @KeepFieldType
      CheckoutViewModel com_bookstore_ui_checkout_CheckoutViewModel2;
    }
  }

  private static final class ViewModelCImpl extends BookStoreApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<CartViewModel> cartViewModelProvider;

    private Provider<CatalogueViewModel> catalogueViewModelProvider;

    private Provider<CheckoutViewModel> checkoutViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<OrdersViewModel> ordersViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<SessionViewModel> sessionViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private LoginUseCase loginUseCase() {
      return new LoginUseCase(singletonCImpl.authRepositoryImplProvider.get());
    }

    private RegisterUseCase registerUseCase() {
      return new RegisterUseCase(singletonCImpl.authRepositoryImplProvider.get());
    }

    private LogoutUseCase logoutUseCase() {
      return new LogoutUseCase(singletonCImpl.authRepositoryImplProvider.get());
    }

    private GetCartUseCase getCartUseCase() {
      return new GetCartUseCase(singletonCImpl.cartRepositoryImplProvider.get());
    }

    private AddToCartUseCase addToCartUseCase() {
      return new AddToCartUseCase(singletonCImpl.cartRepositoryImplProvider.get());
    }

    private RemoveFromCartUseCase removeFromCartUseCase() {
      return new RemoveFromCartUseCase(singletonCImpl.cartRepositoryImplProvider.get());
    }

    private UpdateCartQuantityUseCase updateCartQuantityUseCase() {
      return new UpdateCartQuantityUseCase(singletonCImpl.cartRepositoryImplProvider.get());
    }

    private GetRecommendedBooksUseCase getRecommendedBooksUseCase() {
      return new GetRecommendedBooksUseCase(singletonCImpl.bookRepositoryImplProvider.get(), singletonCImpl.orderRepositoryImplProvider.get());
    }

    private GetCategoriesUseCase getCategoriesUseCase() {
      return new GetCategoriesUseCase(singletonCImpl.bookRepositoryImplProvider.get());
    }

    private GetBooksByCategoryUseCase getBooksByCategoryUseCase() {
      return new GetBooksByCategoryUseCase(singletonCImpl.bookRepositoryImplProvider.get());
    }

    private GetBookDetailUseCase getBookDetailUseCase() {
      return new GetBookDetailUseCase(singletonCImpl.bookRepositoryImplProvider.get());
    }

    private GetRelatedBooksUseCase getRelatedBooksUseCase() {
      return new GetRelatedBooksUseCase(singletonCImpl.bookRepositoryImplProvider.get());
    }

    private SearchBooksUseCase searchBooksUseCase() {
      return new SearchBooksUseCase(singletonCImpl.bookRepositoryImplProvider.get());
    }

    private GetAddressesUseCase getAddressesUseCase() {
      return new GetAddressesUseCase(singletonCImpl.userRepositoryImplProvider.get());
    }

    private AddAddressUseCase addAddressUseCase() {
      return new AddAddressUseCase(singletonCImpl.userRepositoryImplProvider.get());
    }

    private PlaceOrderUseCase placeOrderUseCase() {
      return new PlaceOrderUseCase(singletonCImpl.orderRepositoryImplProvider.get(), singletonCImpl.cartRepositoryImplProvider.get(), singletonCImpl.userRepositoryImplProvider.get());
    }

    private GetFeaturedBooksUseCase getFeaturedBooksUseCase() {
      return new GetFeaturedBooksUseCase(singletonCImpl.bookRepositoryImplProvider.get());
    }

    private GetOrderHistoryUseCase getOrderHistoryUseCase() {
      return new GetOrderHistoryUseCase(singletonCImpl.orderRepositoryImplProvider.get());
    }

    private CancelOrderUseCase cancelOrderUseCase() {
      return new CancelOrderUseCase(singletonCImpl.orderRepositoryImplProvider.get());
    }

    private BuyAgainUseCase buyAgainUseCase() {
      return new BuyAgainUseCase(singletonCImpl.orderRepositoryImplProvider.get(), singletonCImpl.cartRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.cartViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.catalogueViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.checkoutViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.ordersViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.sessionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(8).put(LazyClassKeyProvider.com_bookstore_ui_auth_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_bookstore_ui_cart_CartViewModel, ((Provider) cartViewModelProvider)).put(LazyClassKeyProvider.com_bookstore_ui_catalogue_CatalogueViewModel, ((Provider) catalogueViewModelProvider)).put(LazyClassKeyProvider.com_bookstore_ui_checkout_CheckoutViewModel, ((Provider) checkoutViewModelProvider)).put(LazyClassKeyProvider.com_bookstore_ui_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_bookstore_ui_orders_OrdersViewModel, ((Provider) ordersViewModelProvider)).put(LazyClassKeyProvider.com_bookstore_ui_profile_ProfileViewModel, ((Provider) profileViewModelProvider)).put(LazyClassKeyProvider.com_bookstore_ui_navigation_SessionViewModel, ((Provider) sessionViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_bookstore_ui_cart_CartViewModel = "com.bookstore.ui.cart.CartViewModel";

      static String com_bookstore_ui_checkout_CheckoutViewModel = "com.bookstore.ui.checkout.CheckoutViewModel";

      static String com_bookstore_ui_auth_AuthViewModel = "com.bookstore.ui.auth.AuthViewModel";

      static String com_bookstore_ui_home_HomeViewModel = "com.bookstore.ui.home.HomeViewModel";

      static String com_bookstore_ui_profile_ProfileViewModel = "com.bookstore.ui.profile.ProfileViewModel";

      static String com_bookstore_ui_catalogue_CatalogueViewModel = "com.bookstore.ui.catalogue.CatalogueViewModel";

      static String com_bookstore_ui_orders_OrdersViewModel = "com.bookstore.ui.orders.OrdersViewModel";

      static String com_bookstore_ui_navigation_SessionViewModel = "com.bookstore.ui.navigation.SessionViewModel";

      @KeepFieldType
      CartViewModel com_bookstore_ui_cart_CartViewModel2;

      @KeepFieldType
      CheckoutViewModel com_bookstore_ui_checkout_CheckoutViewModel2;

      @KeepFieldType
      AuthViewModel com_bookstore_ui_auth_AuthViewModel2;

      @KeepFieldType
      HomeViewModel com_bookstore_ui_home_HomeViewModel2;

      @KeepFieldType
      ProfileViewModel com_bookstore_ui_profile_ProfileViewModel2;

      @KeepFieldType
      CatalogueViewModel com_bookstore_ui_catalogue_CatalogueViewModel2;

      @KeepFieldType
      OrdersViewModel com_bookstore_ui_orders_OrdersViewModel2;

      @KeepFieldType
      SessionViewModel com_bookstore_ui_navigation_SessionViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.bookstore.ui.auth.AuthViewModel 
          return (T) new AuthViewModel(viewModelCImpl.loginUseCase(), viewModelCImpl.registerUseCase(), viewModelCImpl.logoutUseCase(), singletonCImpl.sessionDataStoreProvider.get());

          case 1: // com.bookstore.ui.cart.CartViewModel 
          return (T) new CartViewModel(viewModelCImpl.getCartUseCase(), viewModelCImpl.addToCartUseCase(), viewModelCImpl.removeFromCartUseCase(), viewModelCImpl.updateCartQuantityUseCase(), viewModelCImpl.getRecommendedBooksUseCase(), singletonCImpl.sessionDataStoreProvider.get());

          case 2: // com.bookstore.ui.catalogue.CatalogueViewModel 
          return (T) new CatalogueViewModel(viewModelCImpl.getCategoriesUseCase(), viewModelCImpl.getBooksByCategoryUseCase(), viewModelCImpl.getBookDetailUseCase(), viewModelCImpl.getRelatedBooksUseCase(), viewModelCImpl.searchBooksUseCase(), viewModelCImpl.addToCartUseCase(), singletonCImpl.sessionDataStoreProvider.get());

          case 3: // com.bookstore.ui.checkout.CheckoutViewModel 
          return (T) new CheckoutViewModel(viewModelCImpl.getCartUseCase(), viewModelCImpl.getAddressesUseCase(), viewModelCImpl.addAddressUseCase(), new ProcessPaymentUseCase(), viewModelCImpl.placeOrderUseCase(), new RedeemGiftPointsUseCase(), singletonCImpl.userRepositoryImplProvider.get(), singletonCImpl.sessionDataStoreProvider.get());

          case 4: // com.bookstore.ui.home.HomeViewModel 
          return (T) new HomeViewModel(viewModelCImpl.getFeaturedBooksUseCase(), viewModelCImpl.getCategoriesUseCase(), viewModelCImpl.getRecommendedBooksUseCase(), viewModelCImpl.getOrderHistoryUseCase(), viewModelCImpl.addToCartUseCase(), singletonCImpl.sessionDataStoreProvider.get());

          case 5: // com.bookstore.ui.orders.OrdersViewModel 
          return (T) new OrdersViewModel(viewModelCImpl.getOrderHistoryUseCase(), viewModelCImpl.cancelOrderUseCase(), viewModelCImpl.buyAgainUseCase(), singletonCImpl.sessionDataStoreProvider.get());

          case 6: // com.bookstore.ui.profile.ProfileViewModel 
          return (T) new ProfileViewModel(viewModelCImpl.logoutUseCase(), viewModelCImpl.getAddressesUseCase(), singletonCImpl.userRepositoryImplProvider.get(), singletonCImpl.sessionDataStoreProvider.get());

          case 7: // com.bookstore.ui.navigation.SessionViewModel 
          return (T) new SessionViewModel(singletonCImpl.sessionDataStoreProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends BookStoreApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends BookStoreApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends BookStoreApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<BookStoreDatabase> provideDatabaseProvider;

    private Provider<SessionDataStore> sessionDataStoreProvider;

    private Provider<AuthRepositoryImpl> authRepositoryImplProvider;

    private Provider<CartRepositoryImpl> cartRepositoryImplProvider;

    private Provider<BookRepositoryImpl> bookRepositoryImplProvider;

    private Provider<OrderRepositoryImpl> orderRepositoryImplProvider;

    private Provider<UserRepositoryImpl> userRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private UserDao userDao() {
      return DatabaseModule_ProvideUserDaoFactory.provideUserDao(provideDatabaseProvider.get());
    }

    private CartDao cartDao() {
      return DatabaseModule_ProvideCartDaoFactory.provideCartDao(provideDatabaseProvider.get());
    }

    private BookDao bookDao() {
      return DatabaseModule_ProvideBookDaoFactory.provideBookDao(provideDatabaseProvider.get());
    }

    private OrderDao orderDao() {
      return DatabaseModule_ProvideOrderDaoFactory.provideOrderDao(provideDatabaseProvider.get());
    }

    private AddressDao addressDao() {
      return DatabaseModule_ProvideAddressDaoFactory.provideAddressDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<BookStoreDatabase>(singletonCImpl, 1));
      this.sessionDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<SessionDataStore>(singletonCImpl, 2));
      this.authRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepositoryImpl>(singletonCImpl, 0));
      this.cartRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CartRepositoryImpl>(singletonCImpl, 3));
      this.bookRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<BookRepositoryImpl>(singletonCImpl, 4));
      this.orderRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<OrderRepositoryImpl>(singletonCImpl, 5));
      this.userRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<UserRepositoryImpl>(singletonCImpl, 6));
    }

    @Override
    public void injectBookStoreApp(BookStoreApp bookStoreApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.bookstore.data.repository.AuthRepositoryImpl 
          return (T) new AuthRepositoryImpl(singletonCImpl.userDao(), singletonCImpl.sessionDataStoreProvider.get());

          case 1: // com.bookstore.data.local.BookStoreDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.bookstore.data.local.SessionDataStore 
          return (T) new SessionDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.bookstore.data.repository.CartRepositoryImpl 
          return (T) new CartRepositoryImpl(singletonCImpl.cartDao(), singletonCImpl.bookDao());

          case 4: // com.bookstore.data.repository.BookRepositoryImpl 
          return (T) new BookRepositoryImpl(singletonCImpl.bookDao());

          case 5: // com.bookstore.data.repository.OrderRepositoryImpl 
          return (T) new OrderRepositoryImpl(singletonCImpl.orderDao(), singletonCImpl.bookDao());

          case 6: // com.bookstore.data.repository.UserRepositoryImpl 
          return (T) new UserRepositoryImpl(singletonCImpl.userDao(), singletonCImpl.addressDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
