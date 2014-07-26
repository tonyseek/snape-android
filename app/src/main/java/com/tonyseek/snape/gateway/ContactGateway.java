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
        ContactsContract.RawContacts._ID,
        ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
        ContactsContract.RawContacts.CONTACT_ID,
    };

    public ContactGateway(Context context) {
        super(context);
    }

    public ContactData getRawContact(int personId) {
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        String selection = ContactsContract.RawContacts._ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(personId) };

        Cursor cursor = getContentResolver().query(uri, mColumns, selection, selectionArgs, null);

        try {
            if (!cursor.moveToFirst()) {
                return new ContactData(0, "", null);
            }

            int indexId = cursor.getColumnIndex(mColumns[0]);
            int indexDisplayName = cursor.getColumnIndex(mColumns[1]);
            int indexContactId = cursor.getColumnIndex(mColumns[2]);

            int id = cursor.getInt(indexId);
            String displayName = cursor.getString(indexDisplayName);
            int contactId = cursor.getInt(indexContactId);

            return new ContactData(id, displayName, getContactPhoto(contactId));
        } finally {
            cursor.close();
        }
    }

    protected Bitmap getContactPhoto(int contactId) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(
            getContentResolver(), uri);
        Bitmap contactPhoto = BitmapFactory.decodeStream(is);
        return contactPhoto;
    }
}
