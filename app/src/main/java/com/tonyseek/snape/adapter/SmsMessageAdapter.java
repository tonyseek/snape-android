package com.tonyseek.snape.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonyseek.snape.R;
import com.tonyseek.snape.gateway.ContactGateway;
import com.tonyseek.snape.model.ContactData;
import com.tonyseek.snape.model.SmsMessage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SmsMessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<SmsMessage> mObjects;
    private LayoutInflater mInflater;
    private ContactGateway mContactGateway;

    public SmsMessageAdapter(Context context, List<SmsMessage> objects) {
        mContext = context;
        mObjects = objects;
        mInflater = LayoutInflater.from(mContext);
        mContactGateway = new ContactGateway(mContext);
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_message, null);
            holder = new ViewHolder(view, position);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.invalidate();

        return view;
    }

    class ViewHolder {
        private int mPosition;

        @InjectView(R.id.item_message_avatar)
        ImageView mAvatarView;

        @InjectView(R.id.item_message_person)
        TextView mPersonView;

        @InjectView(R.id.item_message_text)
        TextView mTextView;

        ViewHolder(View view, int position) {
            mPosition = position;
            ButterKnife.inject(this, view);
        }

        public void invalidate() {
            SmsMessage smsMessage = (SmsMessage) getItem(mPosition);
            ContactData contactData = mContactGateway.getRawContact(smsMessage.getPersonId());
            Bitmap contactPhoto = contactData.getPhoto();

            if (contactData.getId() == 0) {
                mPersonView.setText(smsMessage.getAddress());
            } else {
                mPersonView.setText(contactData.getDisplayName());
            }

            if (contactPhoto != null) {
                mAvatarView.setImageBitmap(contactData.getPhoto());
            }

            mTextView.setText(smsMessage.getTextBody());
        }
    }
}
