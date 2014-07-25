package com.tonyseek.snape.gateway;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.tonyseek.snape.model.SmsMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsGateway {
    public enum SortOrder {
        DATE_DESC("date desc");

        private String mStatement;

        SortOrder(String statement) {
            mStatement = statement;
        }

        protected String getStatement() {
            return mStatement;
        }
    }

    private final static Uri mInboxQueryUri = Uri.parse("content://sms/inbox");
    private final static String[] mColumns = new String[] {
        "_id", "address", "person", "body", "date", "type"
    };

    private ContentResolver mContentResolver;

    public SmsGateway(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public List<SmsMessage> queryInbox(SortOrder sortOrder) {
        return query(mInboxQueryUri, sortOrder.getStatement());
    }

    protected List<SmsMessage> query(Uri uri, String sortOrder) {
        Cursor cursor = mContentResolver.query(uri, mColumns, null, null, sortOrder);
        List<SmsMessage> resultSet = new ArrayList<SmsMessage>();

        if (!cursor.moveToFirst()) {
            return resultSet;
        }

        do {
            resultSet.add(fetch(cursor));
        } while (cursor.moveToNext());

        return resultSet;
    }

    protected SmsMessage fetch(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        String address = cursor.getString(cursor.getColumnIndex("address"));
        int person = cursor.getInt(cursor.getColumnIndex("person"));
        String body = cursor.getString(cursor.getColumnIndex("body"));
        long dateAsLong = cursor.getLong(cursor.getColumnIndex("date"));
        int typeAsInt = cursor.getInt(cursor.getColumnIndex("type"));

        Date date = new Date(dateAsLong);
        SmsMessage.Type type = SmsMessage.Type.fromInt(typeAsInt);

        return new SmsMessage(id, address, person, body, date, type);
    }
}
