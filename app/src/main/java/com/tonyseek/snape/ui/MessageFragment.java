package com.tonyseek.snape.ui;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonyseek.snape.R;
import com.tonyseek.snape.gateway.ContactGateway;
import com.tonyseek.snape.model.ContactData;
import com.tonyseek.snape.model.SmsMessage;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MessageFragment extends DialogFragment {
    private final static String EXTRA_SMS_MESSAGE = "EXTRA_SMS_MESSAGE";

    private ContactGateway mContactGateway;
    private SmsMessage mSmsMessage;
    private ContactData mContactData;

    @InjectView(R.id.message_avatar)
    ImageView mPhotoView;

    @InjectView(R.id.message_person)
    TextView mPersonView;

    @InjectView(R.id.message_address)
    TextView mAddressView;

    @InjectView(R.id.message_text)
    TextView mTextView;

    @InjectView(R.id.message_date)
    TextView mDateView;

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

        mContactGateway = new ContactGateway(getActivity());

        Bundle args = getArguments();
        mSmsMessage = args.getParcelable(EXTRA_SMS_MESSAGE);
        mContactData = mContactGateway.getRawContact(mSmsMessage.getPersonId());

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.inject(this, view);
        bindView();
        return view;
    }

    @OnClick(R.id.message_button_report)
    public void onReportClick() {

    }

    @OnClick(R.id.message_button_enqueue)
    public void onEnqueueClick() {

    }

    protected void bindView() {
        Bitmap photo = mContactData.getPhoto();
        String displayDate = DateUtils.formatDateTime(
                getActivity(), mSmsMessage.getDate().getTime(), DateUtils.FORMAT_ABBREV_ALL);

        if (mSmsMessage.getPersonId() == 0) {
            mPersonView.setText(R.string.message_unknown_person);
        } else {
            mPersonView.setText(mContactData.getDisplayName());
        }

        if (photo == null) {
            mPhotoView.setImageResource(R.drawable.ic_contact_picture);
        } else {
            mPhotoView.setImageBitmap(photo);
        }

        mAddressView.setText(mSmsMessage.getAddress());
        mTextView.setText(mSmsMessage.getTextBody());
        mDateView.setText(displayDate);
    }
}
