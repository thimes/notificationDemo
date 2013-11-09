package com.himes.demo.notifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class NotificationClickedActivity extends Activity {

    private static final String EXTRA_INFORMATION = "NotificationClickedActivity.information_extra";

    public static Intent createIntent(Context context, String information) {
        Intent intent = new Intent(context, NotificationClickedActivity.class);
        intent.putExtra(EXTRA_INFORMATION, information);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String extraInformation = getIntent().getExtras().getString(EXTRA_INFORMATION);
        Toast.makeText(this, extraInformation, Toast.LENGTH_LONG).show();
    }

}
