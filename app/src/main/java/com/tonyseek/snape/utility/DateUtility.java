package com.tonyseek.snape.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
    public enum Format {
        SIMPLE_DATE("yyyy-MM-dd"),
        SIMPLE_DATETIME("yyyy-MM-dd HH:mm");

        private DateFormat mDateFormat;

        Format(String expr) {
            mDateFormat = new SimpleDateFormat(expr);
        }

        private String format(Date date) {
            return mDateFormat.format(date);
        }
    }

    public static String formatDate(Date date, Format format) {
        return format.format(date);
    }
}
