package edu.washington.xyju.awty2;

import android.app.IntentService;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by xyju on 15/5/23.
 */
public class SendMessageService extends IntentService{

    String phoneNo;
    String sms;

    public SendMessageService() {
        super("SendMessageService");
    }

    @Override
    public void onCreate() { super.onCreate(); }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        phoneNo = intent.getStringExtra("Phone");
        sms = intent.getStringExtra("message");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);

            Log.i("SendMessageService", "Message Sent");

        } catch (Exception e) {
            Log.i("SendMessageService", "Sent Failed");
            e.printStackTrace();
        }
    }

}
