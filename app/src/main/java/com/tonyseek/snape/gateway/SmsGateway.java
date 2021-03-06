package com.tonyseek.snape.gateway;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.tonyseek.snape.model.SmsMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsGateway extends BaseGateway {
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

    public SmsGateway(Context context) {
        super(context);
    }

    public List<SmsMessage> queryInbox(SortOrder sortOrder) {
        return query(mInboxQueryUri, sortOrder.getStatement());
    }


    protected List<SmsMessage> query(Uri uri, String sortOrder) {
        Cursor cursor = getContentResolver().query(uri, mColumns, null, null, sortOrder);
        List<SmsMessage> resultSet = new ArrayList<SmsMessage>();

        try {
            if (!cursor.moveToFirst()) {
                return resultSet;
            }

            do {
                resultSet.add(fetch(cursor));
            } while (cursor.moveToNext());

            return resultSet;
        } finally {
            cursor.close();
        }
    }

    protected SmsMessage fetch(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(mColumns[0]));
        String address = cursor.getString(cursor.getColumnIndex(mColumns[1]));
        int person = cursor.getInt(cursor.getColumnIndex(mColumns[2]));
        String body = cursor.getString(cursor.getColumnIndex(mColumns[3]));
        long dateAsLong = cursor.getLong(cursor.getColumnIndex(mColumns[4]));
        int typeAsInt = cursor.getInt(cursor.getColumnIndex(mColumns[5]));

        Date date = new Date(dateAsLong);
        SmsMessage.Type type = SmsMessage.Type.fromInt(typeAsInt);

        return new SmsMessage(id, address, person, body, date, type);
    }
}
