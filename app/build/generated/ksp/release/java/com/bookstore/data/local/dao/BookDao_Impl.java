package com.bookstore.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bookstore.data.local.entity.BookEntity;
import com.bookstore.data.local.entity.CategoryEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BookDao_Impl implements BookDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BookEntity> __insertionAdapterOfBookEntity;

  private final EntityInsertionAdapter<CategoryEntity> __insertionAdapterOfCategoryEntity;

  public BookDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBookEntity = new EntityInsertionAdapter<BookEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `books` (`id`,`title`,`author`,`description`,`price`,`originalPrice`,`coverImageUrl`,`categoryId`,`brand`,`rating`,`ratingCount`,`deliveryDays`,`stockCount`,`isFeatured`,`isbn`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BookEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getAuthor());
        statement.bindString(4, entity.getDescription());
        statement.bindDouble(5, entity.getPrice());
        statement.bindDouble(6, entity.getOriginalPrice());
        statement.bindString(7, entity.getCoverImageUrl());
        statement.bindLong(8, entity.getCategoryId());
        statement.bindString(9, entity.getBrand());
        statement.bindDouble(10, entity.getRating());
        statement.bindLong(11, entity.getRatingCount());
        statement.bindString(12, entity.getDeliveryDays());
        statement.bindLong(13, entity.getStockCount());
        final int _tmp = entity.isFeatured() ? 1 : 0;
        statement.bindLong(14, _tmp);
        statement.bindString(15, entity.getIsbn());
      }
    };
    this.__insertionAdapterOfCategoryEntity = new EntityInsertionAdapter<CategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `categories` (`id`,`name`,`iconEmoji`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CategoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getIconEmoji());
      }
    };
  }

  @Override
  public Object insertBooks(final List<BookEntity> books,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBookEntity.insert(books);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCategories(final List<CategoryEntity> categories,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCategoryEntity.insert(categories);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CategoryEntity>> getAllCategories() {
    final String _sql = "SELECT * FROM categories ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categories"}, new Callable<List<CategoryEntity>>() {
      @Override
      @NonNull
      public List<CategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "iconEmoji");
          final List<CategoryEntity> _result = new ArrayList<CategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpIconEmoji;
            _tmpIconEmoji = _cursor.getString(_cursorIndexOfIconEmoji);
            _item = new CategoryEntity(_tmpId,_tmpName,_tmpIconEmoji);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BookEntity>> getBooksByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM books WHERE categoryId = ? ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfOriginalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "originalPrice");
          final int _cursorIndexOfCoverImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUrl");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
          final int _cursorIndexOfDeliveryDays = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryDays");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final double _tmpOriginalPrice;
            _tmpOriginalPrice = _cursor.getDouble(_cursorIndexOfOriginalPrice);
            final String _tmpCoverImageUrl;
            _tmpCoverImageUrl = _cursor.getString(_cursorIndexOfCoverImageUrl);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpBrand;
            _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpRatingCount;
            _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
            final String _tmpDeliveryDays;
            _tmpDeliveryDays = _cursor.getString(_cursorIndexOfDeliveryDays);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final String _tmpIsbn;
            _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpCoverImageUrl,_tmpCategoryId,_tmpBrand,_tmpRating,_tmpRatingCount,_tmpDeliveryDays,_tmpStockCount,_tmpIsFeatured,_tmpIsbn);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getBookById(final long bookId, final Continuation<? super BookEntity> $completion) {
    final String _sql = "SELECT * FROM books WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, bookId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BookEntity>() {
      @Override
      @Nullable
      public BookEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfOriginalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "originalPrice");
          final int _cursorIndexOfCoverImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUrl");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
          final int _cursorIndexOfDeliveryDays = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryDays");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final BookEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final double _tmpOriginalPrice;
            _tmpOriginalPrice = _cursor.getDouble(_cursorIndexOfOriginalPrice);
            final String _tmpCoverImageUrl;
            _tmpCoverImageUrl = _cursor.getString(_cursorIndexOfCoverImageUrl);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpBrand;
            _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpRatingCount;
            _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
            final String _tmpDeliveryDays;
            _tmpDeliveryDays = _cursor.getString(_cursorIndexOfDeliveryDays);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final String _tmpIsbn;
            _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            _result = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpCoverImageUrl,_tmpCategoryId,_tmpBrand,_tmpRating,_tmpRatingCount,_tmpDeliveryDays,_tmpStockCount,_tmpIsFeatured,_tmpIsbn);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BookEntity>> getRelatedBooks(final long categoryId, final long excludeBookId) {
    final String _sql = "SELECT * FROM books WHERE categoryId = ? AND id != ? ORDER BY rating DESC LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, excludeBookId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfOriginalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "originalPrice");
          final int _cursorIndexOfCoverImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUrl");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
          final int _cursorIndexOfDeliveryDays = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryDays");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final double _tmpOriginalPrice;
            _tmpOriginalPrice = _cursor.getDouble(_cursorIndexOfOriginalPrice);
            final String _tmpCoverImageUrl;
            _tmpCoverImageUrl = _cursor.getString(_cursorIndexOfCoverImageUrl);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpBrand;
            _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpRatingCount;
            _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
            final String _tmpDeliveryDays;
            _tmpDeliveryDays = _cursor.getString(_cursorIndexOfDeliveryDays);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final String _tmpIsbn;
            _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpCoverImageUrl,_tmpCategoryId,_tmpBrand,_tmpRating,_tmpRatingCount,_tmpDeliveryDays,_tmpStockCount,_tmpIsFeatured,_tmpIsbn);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BookEntity>> getFeaturedBooks() {
    final String _sql = "SELECT * FROM books WHERE isFeatured = 1 ORDER BY rating DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfOriginalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "originalPrice");
          final int _cursorIndexOfCoverImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUrl");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
          final int _cursorIndexOfDeliveryDays = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryDays");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final double _tmpOriginalPrice;
            _tmpOriginalPrice = _cursor.getDouble(_cursorIndexOfOriginalPrice);
            final String _tmpCoverImageUrl;
            _tmpCoverImageUrl = _cursor.getString(_cursorIndexOfCoverImageUrl);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpBrand;
            _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpRatingCount;
            _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
            final String _tmpDeliveryDays;
            _tmpDeliveryDays = _cursor.getString(_cursorIndexOfDeliveryDays);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final String _tmpIsbn;
            _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpCoverImageUrl,_tmpCategoryId,_tmpBrand,_tmpRating,_tmpRatingCount,_tmpDeliveryDays,_tmpStockCount,_tmpIsFeatured,_tmpIsbn);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BookEntity>> searchBooks(final String query) {
    final String _sql = "SELECT * FROM books WHERE title LIKE '%' || ? || '%' OR author LIKE '%' || ? || '%' ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfOriginalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "originalPrice");
          final int _cursorIndexOfCoverImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUrl");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
          final int _cursorIndexOfDeliveryDays = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryDays");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final double _tmpOriginalPrice;
            _tmpOriginalPrice = _cursor.getDouble(_cursorIndexOfOriginalPrice);
            final String _tmpCoverImageUrl;
            _tmpCoverImageUrl = _cursor.getString(_cursorIndexOfCoverImageUrl);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpBrand;
            _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpRatingCount;
            _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
            final String _tmpDeliveryDays;
            _tmpDeliveryDays = _cursor.getString(_cursorIndexOfDeliveryDays);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final String _tmpIsbn;
            _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpCoverImageUrl,_tmpCategoryId,_tmpBrand,_tmpRating,_tmpRatingCount,_tmpDeliveryDays,_tmpStockCount,_tmpIsFeatured,_tmpIsbn);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BookEntity>> getAllBooks() {
    final String _sql = "SELECT * FROM books ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfOriginalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "originalPrice");
          final int _cursorIndexOfCoverImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUrl");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
          final int _cursorIndexOfDeliveryDays = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryDays");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final double _tmpOriginalPrice;
            _tmpOriginalPrice = _cursor.getDouble(_cursorIndexOfOriginalPrice);
            final String _tmpCoverImageUrl;
            _tmpCoverImageUrl = _cursor.getString(_cursorIndexOfCoverImageUrl);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpBrand;
            _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpRatingCount;
            _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
            final String _tmpDeliveryDays;
            _tmpDeliveryDays = _cursor.getString(_cursorIndexOfDeliveryDays);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final String _tmpIsbn;
            _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpCoverImageUrl,_tmpCategoryId,_tmpBrand,_tmpRating,_tmpRatingCount,_tmpDeliveryDays,_tmpStockCount,_tmpIsFeatured,_tmpIsbn);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getBooksCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM books";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
