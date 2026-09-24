package com.bookstore.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.bookstore.data.local.dao.AddressDao;
import com.bookstore.data.local.dao.AddressDao_Impl;
import com.bookstore.data.local.dao.BookDao;
import com.bookstore.data.local.dao.BookDao_Impl;
import com.bookstore.data.local.dao.CartDao;
import com.bookstore.data.local.dao.CartDao_Impl;
import com.bookstore.data.local.dao.OrderDao;
import com.bookstore.data.local.dao.OrderDao_Impl;
import com.bookstore.data.local.dao.UserDao;
import com.bookstore.data.local.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BookStoreDatabase_Impl extends BookStoreDatabase {
  private volatile UserDao _userDao;

  private volatile AddressDao _addressDao;

  private volatile BookDao _bookDao;

  private volatile CartDao _cartDao;

  private volatile OrderDao _orderDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `passwordHash` TEXT NOT NULL, `giftPoints` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `addresses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `label` TEXT NOT NULL, `fullAddress` TEXT NOT NULL, `city` TEXT NOT NULL, `pincode` TEXT NOT NULL, `isDefault` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `iconEmoji` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `books` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `author` TEXT NOT NULL, `description` TEXT NOT NULL, `price` REAL NOT NULL, `originalPrice` REAL NOT NULL, `coverImageUrl` TEXT NOT NULL, `categoryId` INTEGER NOT NULL, `brand` TEXT NOT NULL, `rating` REAL NOT NULL, `ratingCount` INTEGER NOT NULL, `deliveryDays` TEXT NOT NULL, `stockCount` INTEGER NOT NULL, `isFeatured` INTEGER NOT NULL, `isbn` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cart_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `bookId` INTEGER NOT NULL, `quantity` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `orders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `totalAmount` REAL NOT NULL, `discountAmount` REAL NOT NULL, `deliveryFee` REAL NOT NULL, `status` TEXT NOT NULL, `placedAt` INTEGER NOT NULL, `deliveryAddress` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `order_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `orderId` INTEGER NOT NULL, `bookId` INTEGER NOT NULL, `quantity` INTEGER NOT NULL, `unitPrice` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '95ef79834d040bf74be818522d17f280')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `addresses`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `books`");
        db.execSQL("DROP TABLE IF EXISTS `cart_items`");
        db.execSQL("DROP TABLE IF EXISTS `orders`");
        db.execSQL("DROP TABLE IF EXISTS `order_items`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(5);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("passwordHash", new TableInfo.Column("passwordHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("giftPoints", new TableInfo.Column("giftPoints", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.bookstore.data.local.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsAddresses = new HashMap<String, TableInfo.Column>(7);
        _columnsAddresses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("fullAddress", new TableInfo.Column("fullAddress", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("city", new TableInfo.Column("city", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("pincode", new TableInfo.Column("pincode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("isDefault", new TableInfo.Column("isDefault", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAddresses = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAddresses = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAddresses = new TableInfo("addresses", _columnsAddresses, _foreignKeysAddresses, _indicesAddresses);
        final TableInfo _existingAddresses = TableInfo.read(db, "addresses");
        if (!_infoAddresses.equals(_existingAddresses)) {
          return new RoomOpenHelper.ValidationResult(false, "addresses(com.bookstore.data.local.entity.AddressEntity).\n"
                  + " Expected:\n" + _infoAddresses + "\n"
                  + " Found:\n" + _existingAddresses);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(3);
        _columnsCategories.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("iconEmoji", new TableInfo.Column("iconEmoji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.bookstore.data.local.entity.CategoryEntity).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsBooks = new HashMap<String, TableInfo.Column>(15);
        _columnsBooks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("author", new TableInfo.Column("author", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("originalPrice", new TableInfo.Column("originalPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("coverImageUrl", new TableInfo.Column("coverImageUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("brand", new TableInfo.Column("brand", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("rating", new TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("ratingCount", new TableInfo.Column("ratingCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("deliveryDays", new TableInfo.Column("deliveryDays", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("stockCount", new TableInfo.Column("stockCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("isFeatured", new TableInfo.Column("isFeatured", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("isbn", new TableInfo.Column("isbn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBooks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBooks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBooks = new TableInfo("books", _columnsBooks, _foreignKeysBooks, _indicesBooks);
        final TableInfo _existingBooks = TableInfo.read(db, "books");
        if (!_infoBooks.equals(_existingBooks)) {
          return new RoomOpenHelper.ValidationResult(false, "books(com.bookstore.data.local.entity.BookEntity).\n"
                  + " Expected:\n" + _infoBooks + "\n"
                  + " Found:\n" + _existingBooks);
        }
        final HashMap<String, TableInfo.Column> _columnsCartItems = new HashMap<String, TableInfo.Column>(4);
        _columnsCartItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("bookId", new TableInfo.Column("bookId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCartItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCartItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCartItems = new TableInfo("cart_items", _columnsCartItems, _foreignKeysCartItems, _indicesCartItems);
        final TableInfo _existingCartItems = TableInfo.read(db, "cart_items");
        if (!_infoCartItems.equals(_existingCartItems)) {
          return new RoomOpenHelper.ValidationResult(false, "cart_items(com.bookstore.data.local.entity.CartItemEntity).\n"
                  + " Expected:\n" + _infoCartItems + "\n"
                  + " Found:\n" + _existingCartItems);
        }
        final HashMap<String, TableInfo.Column> _columnsOrders = new HashMap<String, TableInfo.Column>(8);
        _columnsOrders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("totalAmount", new TableInfo.Column("totalAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("discountAmount", new TableInfo.Column("discountAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("deliveryFee", new TableInfo.Column("deliveryFee", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("placedAt", new TableInfo.Column("placedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("deliveryAddress", new TableInfo.Column("deliveryAddress", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOrders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOrders = new TableInfo("orders", _columnsOrders, _foreignKeysOrders, _indicesOrders);
        final TableInfo _existingOrders = TableInfo.read(db, "orders");
        if (!_infoOrders.equals(_existingOrders)) {
          return new RoomOpenHelper.ValidationResult(false, "orders(com.bookstore.data.local.entity.OrderEntity).\n"
                  + " Expected:\n" + _infoOrders + "\n"
                  + " Found:\n" + _existingOrders);
        }
        final HashMap<String, TableInfo.Column> _columnsOrderItems = new HashMap<String, TableInfo.Column>(5);
        _columnsOrderItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("orderId", new TableInfo.Column("orderId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("bookId", new TableInfo.Column("bookId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("unitPrice", new TableInfo.Column("unitPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrderItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOrderItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOrderItems = new TableInfo("order_items", _columnsOrderItems, _foreignKeysOrderItems, _indicesOrderItems);
        final TableInfo _existingOrderItems = TableInfo.read(db, "order_items");
        if (!_infoOrderItems.equals(_existingOrderItems)) {
          return new RoomOpenHelper.ValidationResult(false, "order_items(com.bookstore.data.local.entity.OrderItemEntity).\n"
                  + " Expected:\n" + _infoOrderItems + "\n"
                  + " Found:\n" + _existingOrderItems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "95ef79834d040bf74be818522d17f280", "151c0c57fa54f785bc24442fd3b2b659");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","addresses","categories","books","cart_items","orders","order_items");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `addresses`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `books`");
      _db.execSQL("DELETE FROM `cart_items`");
      _db.execSQL("DELETE FROM `orders`");
      _db.execSQL("DELETE FROM `order_items`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AddressDao.class, AddressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BookDao.class, BookDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CartDao.class, CartDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(OrderDao.class, OrderDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public AddressDao addressDao() {
    if (_addressDao != null) {
      return _addressDao;
    } else {
      synchronized(this) {
        if(_addressDao == null) {
          _addressDao = new AddressDao_Impl(this);
        }
        return _addressDao;
      }
    }
  }

  @Override
  public BookDao bookDao() {
    if (_bookDao != null) {
      return _bookDao;
    } else {
      synchronized(this) {
        if(_bookDao == null) {
          _bookDao = new BookDao_Impl(this);
        }
        return _bookDao;
      }
    }
  }

  @Override
  public CartDao cartDao() {
    if (_cartDao != null) {
      return _cartDao;
    } else {
      synchronized(this) {
        if(_cartDao == null) {
          _cartDao = new CartDao_Impl(this);
        }
        return _cartDao;
      }
    }
  }

  @Override
  public OrderDao orderDao() {
    if (_orderDao != null) {
      return _orderDao;
    } else {
      synchronized(this) {
        if(_orderDao == null) {
          _orderDao = new OrderDao_Impl(this);
        }
        return _orderDao;
      }
    }
  }
}
