package com.tonyseek.snape.model;

import java.util.Date;

public class SmsMessage {
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

    public int getPerson() {
        return person;
    }

    public String getBody() {
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
}
