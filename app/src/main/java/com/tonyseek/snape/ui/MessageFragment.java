package com.tonyseek.snape.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonyseek.snape.R;
import com.tonyseek.snape.model.SmsMessage;

import butterknife.ButterKnife;

public class MessageFragment extends DialogFragment {
    private final static String EXTRA_SMS_MESSAGE = "EXTRA_SMS_MESSAGE";

    private SmsMessage mSmsMessage;

    public static MessageFragment getInstance(SmsMessage smsMessage) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_SMS_MESSAGE, smsMessage);

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mSmsMessage = args.getParcelable(EXTRA_SMS_MESSAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.inject(this, view);
        return view;
    }
}
