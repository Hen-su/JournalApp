package com.example.journalapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    NotificationManagerCompat notificationManagerCompat;
    public static final String CHANNEL1_ID = "channel1";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannels();

        Notification notification = new NotificationCompat.Builder(context, CHANNEL1_ID)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("Task Due")
                .setContentText("You have an upcoming task due")
                .setCategory(Notification.CATEGORY_EVENT)
                .build();
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(123, notification);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL1_ID, "channel1", NotificationManager.IMPORTANCE_HIGH);

            Class<NotificationManager> manager = NotificationManager.class;
            notificationManagerCompat.createNotificationChannel(channel1);
        }
    }
}
