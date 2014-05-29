package com.thimes.notificationdemo.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.InboxStyle;
import android.text.format.DateUtils;
import android.widget.RemoteViews;


public class MainActivity extends Activity {


    public static final int STANDARD_TYPE = 0;
    public static final int BIG_TEXT_TYPE = 1;
    public static final int BIG_PICTURE_TYPE = 2;
    public static final int INBOX_TYPE = 3;
    public static final int CUSTOM_TYPE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildNotificationWithBuilder(STANDARD_TYPE);
        buildNotificationWithBuilder(BIG_TEXT_TYPE);
        buildNotificationWithBuilder(BIG_PICTURE_TYPE);
        buildNotificationWithBuilder(INBOX_TYPE);
        buildNotificationWithBuilder(CUSTOM_TYPE);
    }

    private void buildNotificationWithBuilder(int notificationId) {

        Notification notification = null;
        NotificationCompat.Builder builder = getNotificationBuilder(notificationId);

        switch (notificationId) {
            case STANDARD_TYPE:
                notification = builder.build();
                break;
            case BIG_TEXT_TYPE:
                BigTextStyle bigText = new NotificationCompat.BigTextStyle(builder)
                        .bigText("This is a lot of text, and is, therefore, big text, if you see what I mean");
                notification = bigText.build();
                break;
            case BIG_PICTURE_TYPE:
                Bitmap myBigBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.big_image);
                builder.setLargeIcon(myBigBitmap);
                BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle(builder).bigPicture(myBigBitmap);
                notification = bigPicture.build();
                break;
            case INBOX_TYPE:
                InboxStyle inbox = new NotificationCompat.InboxStyle(builder).addLine("First line of the inbox").addLine(
                        "SecondLine of the inbox");
                notification = inbox.build();
                break;
            case CUSTOM_TYPE:
                notification = buildCustomNotification(notificationId);
                break;
        }

        NotificationManager notifMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifMgr.notify(notificationId, notification);
    }

    @SuppressLint("NewApi") // only suppressing it because I know I've checked for it
    private Notification buildCustomNotification(int notificationId) {
        Notification n = new Notification();

        n.icon = R.drawable.ic_stat_5;
        n.when = System.currentTimeMillis();
        n.defaults = Notification.DEFAULT_ALL;
        n.tickerText = "New custom notification has arrived!";

        Bitmap myBigBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.custom_image);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.rich_notification);

        contentView.setImageViewBitmap(R.id.imageL, myBigBitmap);
        contentView.setImageViewBitmap(R.id.imageR, myBigBitmap);
        contentView.setTextViewText(R.id.title, "Hi Notification!");
        contentView.setTextViewText(R.id.text, "Not so bad...right?");

        n.contentView = contentView;
        n.contentIntent = getContentIntentForNotification(CUSTOM_TYPE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            RemoteViews expandedContentView = buildExpandedRemoteViews();
            n.bigContentView = expandedContentView;
        }

        return n;
    }

    private RemoteViews buildExpandedRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.rich_notification_expanded);

        remoteViews.setImageViewResource(R.id.big_cat, R.drawable.big_image);
        remoteViews.setImageViewResource(R.id.cat_tl, R.drawable.cat1);
        remoteViews.setImageViewResource(R.id.cat_tm, R.drawable.cat2);
        remoteViews.setImageViewResource(R.id.cat_tr, R.drawable.cat3);
        remoteViews.setImageViewResource(R.id.cat_ml, R.drawable.cat4);
        remoteViews.setImageViewResource(R.id.cat_mr, R.drawable.cat5);
        remoteViews.setImageViewResource(R.id.cat_bl, R.drawable.cat6);
        remoteViews.setImageViewResource(R.id.cat_bm, R.drawable.cat7);
        remoteViews.setImageViewResource(R.id.cat_br, R.drawable.cat8);


        return remoteViews;
    }

    private PendingIntent getContentIntentForNotification(int notificationId) {
        return PendingIntent.getActivity(this, notificationId,
                NotificationClickedActivity.createIntent(this, Integer.valueOf(notificationId).toString()), Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private NotificationCompat.Builder getNotificationBuilder(int notificationId) {

        int notificationResId = R.drawable.ic_stat_1;
        switch (notificationId) {
            case STANDARD_TYPE:
                notificationResId = R.drawable.ic_stat_1;
                break;
            case BIG_TEXT_TYPE:
                notificationResId = R.drawable.ic_stat_2;
                break;
            case BIG_PICTURE_TYPE:
                notificationResId = R.drawable.ic_stat_3;
                break;
            case INBOX_TYPE:
                notificationResId = R.drawable.ic_stat_4;
                break;
            case CUSTOM_TYPE:
                notificationResId = R.drawable.ic_stat_5;
                break;

        }

        PendingIntent contentIntent = getContentIntentForNotification(notificationId);

        Bitmap mySmallBitmap = BitmapFactory.decodeResource(getResources(), notificationResId);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(mySmallBitmap)
                .setSmallIcon(notificationResId)
                .setTicker("New Notification (" + (notificationId + 1) + ")")
                .setContentTitle("Notification type " + (notificationId + 1) + " title")
                .setContentText("This is the notification type " + notificationId + " text")
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS) // yesterday will always be the time
                .addAction(android.R.drawable.ic_delete, "Delete", getDeleteActionPendingIntent(notificationId));
        return builder;
    }

    private PendingIntent getDeleteActionPendingIntent(int notificationId) {
        PendingIntent deleteIntent = PendingIntent.getService(this, notificationId, DeleteService.createIntent(this, notificationId), 0);
        return deleteIntent;
    }

}
