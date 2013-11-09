package com.himes.demo.notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postNotification(3);
    }

    private void postNotification(int i) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notif = null;
        switch (i) {
            case 3:
                PendingIntent deleteIntent = PendingIntent.getActivity(this, getUniqueId(),
                        NotificationClickedActivity.createIntent(this, "NOTIFICATION DELETED!!!!"), Intent.FLAG_ACTIVITY_CLEAR_TOP);
                builder.setDeleteIntent(deleteIntent);
            case 2:
                builder.setAutoCancel(true);
            case 1:
                PendingIntent contentIntent = PendingIntent.getActivity(this, getUniqueId(),
                        NotificationClickedActivity.createIntent(this, "case " + i), Intent.FLAG_ACTIVITY_CLEAR_TOP);
                builder.setContentIntent(contentIntent);
            case 0:
                // bare minimum needed to notify
                builder.setSmallIcon(android.R.drawable.ic_notification_clear_all)
                       .setContentTitle("content_title")
                       .setContentText("content_text");
                notif = builder.build();
                break;
            default:
                break;
        }

        NotificationManager notifMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notif != null) {
            notifMgr.notify(i, notif);
        }
    }

    private int getUniqueId() {
        return Long.valueOf(System.currentTimeMillis()).intValue();
    }

    public void notifyCompatible(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        
        builder.setSmallIcon(android.R.drawable.ic_notification_clear_all)
               .setContentTitle("content_title")
               .setContentText("content_text");
        
        Notification notif = builder.build();
        NotificationManager notifMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifMgr.notify(0, notif);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
