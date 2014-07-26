package com.tonyseek.snape.gateway;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.tonyseek.snape.model.ContactData;

import java.io.InputStream;

public class ContactGateway extends BaseGateway {
    private final static String[] mColumns = new String[] {
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,
    };

    public ContactGateway(Context context) {
        super(context);
    }

    public ContactData getContactData(int contactId) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Cursor cursor = getContentResolver().query(uri, mColumns, null, null, null);

        if (!cursor.moveToFirst()) {
            return new ContactData(0, "", Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444));
        }

        int indexId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int indexDisplayName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

        int id = cursor.getInt(indexId);
        String displayName = cursor.getString(indexDisplayName);
        Bitmap contactPhoto = getContactPhoto(uri);

        return new ContactData(id, displayName, contactPhoto);
    }

    protected Bitmap getContactPhoto(Uri uri) {
        InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(
            getContentResolver(), uri);
        Bitmap contactPhoto = BitmapFactory.decodeStream(is);
        return contactPhoto;
    }
}
