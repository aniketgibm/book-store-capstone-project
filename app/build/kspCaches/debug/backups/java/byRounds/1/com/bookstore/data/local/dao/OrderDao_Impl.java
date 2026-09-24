package com.bookstore.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bookstore.data.local.entity.OrderEntity;
import com.bookstore.data.local.entity.OrderItemEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OrderEntity> __insertionAdapterOfOrderEntity;

  private final EntityInsertionAdapter<OrderItemEntity> __insertionAdapterOfOrderItemEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateOrderStatus;

  public OrderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrderEntity = new EntityInsertionAdapter<OrderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `orders` (`id`,`userId`,`totalAmount`,`discountAmount`,`deliveryFee`,`status`,`placedAt`,`deliveryAddress`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OrderEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindDouble(3, entity.getTotalAmount());
        statement.bindDouble(4, entity.getDiscountAmount());
        statement.bindDouble(5, entity.getDeliveryFee());
        statement.bindString(6, entity.getStatus());
        statement.bindLong(7, entity.getPlacedAt());
        statement.bindString(8, entity.getDeliveryAddress());
      }
    };
    this.__insertionAdapterOfOrderItemEntity = new EntityInsertionAdapter<OrderItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `order_items` (`id`,`orderId`,`bookId`,`quantity`,`unitPrice`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OrderItemEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getOrderId());
        statement.bindLong(3, entity.getBookId());
        statement.bindLong(4, entity.getQuantity());
        statement.bindDouble(5, entity.getUnitPrice());
      }
    };
    this.__preparedStmtOfUpdateOrderStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET status = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrder(final OrderEntity order, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfOrderEntity.insertAndReturnId(order);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOrderItems(final List<OrderItemEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrderItemEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateOrderStatus(final long orderId, final String status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateOrderStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, orderId);
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
          __preparedStmtOfUpdateOrderStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<OrderEntity>> getOrdersForUser(final long userId) {
    final String _sql = "SELECT * FROM orders WHERE userId = ? ORDER BY placedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"orders"}, new Callable<List<OrderEntity>>() {
      @Override
      @NonNull
      public List<OrderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfDiscountAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "discountAmount");
          final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPlacedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "placedAt");
          final int _cursorIndexOfDeliveryAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryAddress");
          final List<OrderEntity> _result = new ArrayList<OrderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpDiscountAmount;
            _tmpDiscountAmount = _cursor.getDouble(_cursorIndexOfDiscountAmount);
            final double _tmpDeliveryFee;
            _tmpDeliveryFee = _cursor.getDouble(_cursorIndexOfDeliveryFee);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpPlacedAt;
            _tmpPlacedAt = _cursor.getLong(_cursorIndexOfPlacedAt);
            final String _tmpDeliveryAddress;
            _tmpDeliveryAddress = _cursor.getString(_cursorIndexOfDeliveryAddress);
            _item = new OrderEntity(_tmpId,_tmpUserId,_tmpTotalAmount,_tmpDiscountAmount,_tmpDeliveryFee,_tmpStatus,_tmpPlacedAt,_tmpDeliveryAddress);
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
  public Object getOrderById(final long orderId,
      final Continuation<? super OrderEntity> $completion) {
    final String _sql = "SELECT * FROM orders WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, orderId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<OrderEntity>() {
      @Override
      @Nullable
      public OrderEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfDiscountAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "discountAmount");
          final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPlacedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "placedAt");
          final int _cursorIndexOfDeliveryAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryAddress");
          final OrderEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpDiscountAmount;
            _tmpDiscountAmount = _cursor.getDouble(_cursorIndexOfDiscountAmount);
            final double _tmpDeliveryFee;
            _tmpDeliveryFee = _cursor.getDouble(_cursorIndexOfDeliveryFee);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpPlacedAt;
            _tmpPlacedAt = _cursor.getLong(_cursorIndexOfPlacedAt);
            final String _tmpDeliveryAddress;
            _tmpDeliveryAddress = _cursor.getString(_cursorIndexOfDeliveryAddress);
            _result = new OrderEntity(_tmpId,_tmpUserId,_tmpTotalAmount,_tmpDiscountAmount,_tmpDeliveryFee,_tmpStatus,_tmpPlacedAt,_tmpDeliveryAddress);
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
  public Object getOrderItems(final long orderId,
      final Continuation<? super List<OrderItemEntity>> $completion) {
    final String _sql = "SELECT * FROM order_items WHERE orderId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, orderId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<OrderItemEntity>>() {
      @Override
      @NonNull
      public List<OrderItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "bookId");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "unitPrice");
          final List<OrderItemEntity> _result = new ArrayList<OrderItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderItemEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpOrderId;
            _tmpOrderId = _cursor.getLong(_cursorIndexOfOrderId);
            final long _tmpBookId;
            _tmpBookId = _cursor.getLong(_cursorIndexOfBookId);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpUnitPrice;
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice);
            _item = new OrderItemEntity(_tmpId,_tmpOrderId,_tmpBookId,_tmpQuantity,_tmpUnitPrice);
            _result.add(_item);
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
