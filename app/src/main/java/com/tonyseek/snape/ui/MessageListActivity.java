package com.tonyseek.snape.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tonyseek.snape.R;
import com.tonyseek.snape.adapter.SmsMessageAdapter;
import com.tonyseek.snape.gateway.SmsGateway;
import com.tonyseek.snape.model.SmsMessage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;


public class MessageListActivity extends Activity {
    private SmsGateway mSmsGateway = new SmsGateway(this);

    private List<SmsMessage> mSmsMessageList;
    private SmsMessageAdapter mSmsMessageListAdapter;

    private Runnable mInboxUpdater = new Runnable() {
        @Override
        public void run() {
            mSmsMessageList = mSmsGateway.queryInbox(SmsGateway.SortOrder.DATE_DESC);
            // TODO use the user's preference value instead of hard coding value
            mSmsMessageList = mSmsMessageList.subList(0, Math.min(100, mSmsMessageList.size()));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            });
        }
    };

    @InjectView(R.id.message_list)
    ListView mSmsMessageListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.inject(this);

        new Thread(mInboxUpdater).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnItemClick(R.id.message_list)
    public void onMessageClick(AdapterView <?> parent, View view, int position, long id) {
        SmsMessage smsMessage = (SmsMessage) mSmsMessageListAdapter.getItem(position);
        MessageFragment fragment = MessageFragment.getInstance(smsMessage);
        fragment.show(getFragmentManager(), MessageFragment.class.getName());
    }

    protected void invalidate() {
        mSmsMessageListAdapter = new SmsMessageAdapter(this, mSmsMessageList);
        mSmsMessageListView.setAdapter(mSmsMessageListAdapter);
    }
}
