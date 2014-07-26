package com.tonyseek.snape.gateway;

import android.content.ContentResolver;
import android.content.Context;

public abstract class BaseGateway {
    private Context mContext;

    public BaseGateway(Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    protected ContentResolver getContentResolver() {
        return getContext().getContentResolver();
    }
}
