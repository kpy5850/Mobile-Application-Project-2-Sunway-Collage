package com.example.genshintodolist.util;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.genshintodolist.R;
import com.example.genshintodolist.data.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class NotificationHelper {
    private static final String CHANNEL_ID = "todo_channel";
    private static final int PINNED_ID = 1;

    public static void createChannel(Context ctx){
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel ch = new NotificationChannel(
                    CHANNEL_ID, "Task Reminders", NotificationManager.IMPORTANCE_LOW);
            ch.setDescription("Show an ongoing reminder for today's tasks");
            ctx.getSystemService(NotificationManager.class).createNotificationChannel(ch);
        }
    }

    public static void updateTodayPinnedNotification(Context ctx, DBManager db){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Cursor c = db.fetchTitlesForDate(today);
        Set<String> titles = new HashSet<>();
        while(c.moveToNext()){
            titles.add(c.getString(0));
        }
        c.close();

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);

        if(titles.isEmpty()){
            nm.cancel(PINNED_ID);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(String t : titles) sb.append("• " + t);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle().setBigContentTitle("Today's Task");
        for(String t : titles){
            style.addLine("• " + t);
        }

        NotificationCompat.Builder nb = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.traveler_icon)
                .setContentTitle("Today's Task")
                .setContentText("You have " + titles.size() + " task titles(s) today")
                .setStyle(style)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        nm.notify(PINNED_ID, nb.build());
    }
}
