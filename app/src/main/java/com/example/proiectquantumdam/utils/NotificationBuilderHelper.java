package com.example.proiectquantumdam.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.example.proiectquantumdam.MainActivity;
import com.example.proiectquantumdam.R;

public class NotificationBuilderHelper {

    public static Notification createNotificationCompatBuilder(Context context, String channelId, String status) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.backend_icon)
                .setContentTitle("Job updated")
                .setContentText("A job was updated. Status: " + status)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        return builder.build();
    }


    }