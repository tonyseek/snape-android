package com.tonyseek.snape.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class SmsMessage implements Parcelable {
    public enum Type {
        RECEIVED, SENT, UNKNOWN;

        private final static int INT_RECEIVED = 1;
        private final static int INT_SENT = 2;

        public static Type fromInt(int typeAsInt) {
            switch (typeAsInt) {
                case INT_RECEIVED:
                    return RECEIVED;
                case INT_SENT:
                    return SENT;
                default:
                    return UNKNOWN;
            }
        }
    }

    private int id;
    private String address;
    private int person;
    private String body;
    private Date date;
    private Type type;

    public SmsMessage(int id, String address, int person, String body, Date date, Type type) {
        this.id = id;
        this.address = address;
        this.person = person;
        this.body = body;
        this.date = date;
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SmsMessage) {
            return ((SmsMessage) other).id == id;
        }
        return super.equals(other);
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getPersonId() {
        return person;
    }

    public String getTextBody() {
        return body;
    }

    public Date getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SmsMessage{" +
            "id=" + id +
            ", address='" + address + '\'' +
            ", person=" + person +
            ", body='" + body + '\'' +
            ", date=" + date +
            ", type=" + type +
            '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.address);
        dest.writeInt(this.person);
        dest.writeString(this.body);
        dest.writeLong(date != null ? date.getTime() : -1);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    private SmsMessage(Parcel in) {
        this.id = in.readInt();
        this.address = in.readString();
        this.person = in.readInt();
        this.body = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
    }

    public static final Parcelable.Creator<SmsMessage> CREATOR = new Parcelable.Creator<SmsMessage>() {
        public SmsMessage createFromParcel(Parcel source) {
            return new SmsMessage(source);
        }

        public SmsMessage[] newArray(int size) {
            return new SmsMessage[size];
        }
    };
}
