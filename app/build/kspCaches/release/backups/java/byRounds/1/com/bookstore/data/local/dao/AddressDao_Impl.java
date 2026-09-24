package com.bookstore.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bookstore.data.local.entity.AddressEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class AddressDao_Impl implements AddressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AddressEntity> __insertionAdapterOfAddressEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAddress;

  public AddressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAddressEntity = new EntityInsertionAdapter<AddressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `addresses` (`id`,`userId`,`label`,`fullAddress`,`city`,`pincode`,`isDefault`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AddressEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getLabel());
        statement.bindString(4, entity.getFullAddress());
        statement.bindString(5, entity.getCity());
        statement.bindString(6, entity.getPincode());
        final int _tmp = entity.isDefault() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__preparedStmtOfDeleteAddress = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM addresses WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAddress(final AddressEntity address,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAddressEntity.insert(address);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAddress(final long addressId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAddress.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, addressId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAddress.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AddressEntity>> getAddressesForUser(final long userId) {
    final String _sql = "SELECT * FROM addresses WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"addresses"}, new Callable<List<AddressEntity>>() {
      @Override
      @NonNull
      public List<AddressEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfFullAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "fullAddress");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfPincode = CursorUtil.getColumnIndexOrThrow(_cursor, "pincode");
          final int _cursorIndexOfIsDefault = CursorUtil.getColumnIndexOrThrow(_cursor, "isDefault");
          final List<AddressEntity> _result = new ArrayList<AddressEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AddressEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final String _tmpFullAddress;
            _tmpFullAddress = _cursor.getString(_cursorIndexOfFullAddress);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final String _tmpPincode;
            _tmpPincode = _cursor.getString(_cursorIndexOfPincode);
            final boolean _tmpIsDefault;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDefault);
            _tmpIsDefault = _tmp != 0;
            _item = new AddressEntity(_tmpId,_tmpUserId,_tmpLabel,_tmpFullAddress,_tmpCity,_tmpPincode,_tmpIsDefault);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
