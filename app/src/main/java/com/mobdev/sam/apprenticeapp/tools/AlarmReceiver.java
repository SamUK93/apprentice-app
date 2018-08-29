package com.mobdev.sam.apprenticeapp.tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.activites.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Bundle bundle = intent.getExtras();

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(bundle.getString("title"))
                .setContentText(bundle.getString("content"))
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))
                .build();

        notificationManager.notify(4, notification);
    }
}
