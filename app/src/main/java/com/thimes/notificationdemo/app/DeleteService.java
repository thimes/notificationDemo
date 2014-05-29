package com.thimes.notificationdemo.app;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DeleteService extends IntentService {

    private static final String EXTRA_NOTIFICATION_ID = "com.himes.demo.notifications.NOTIFICATION_ID";
    private static final String TAG = "DeleteService";

    public DeleteService() {
        super("DeleteService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "this is where the delete would happen...");
        int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1);
        NotificationManager notifMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notifMgr.cancel(notificationId);
        Toast.makeText(this, "deleted it !!", Toast.LENGTH_SHORT).show();
        Log.v(TAG, "Notification was dismissed");
        // TODO: do more interesting things here, if you want to switch on the action
    }

    public static Intent createIntent(Context context, int notificationId) {
        Intent intent = new Intent(context, DeleteService.class);
        intent.putExtra(EXTRA_NOTIFICATION_ID, notificationId);
        return intent;
    }

}
