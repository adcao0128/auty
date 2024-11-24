package com.auty.modules.applets;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.auty.MainActivity;

public class NotificationApplet extends Applet{

    public String ChannelID;
    public String tag;
    private final Context context;
    private static final int PERMISSION_REQUEST_CODE = 123;

    public NotificationApplet(Context context, String channelID, String tag) {
        super("NotificationApplet", "config");
        this.context = context;
        this.ChannelID = channelID;
        this.tag = tag;

    }

    public void sendNotification(String responseName, String title, String contentText) {
        if (ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(this.context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, this.ChannelID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(contentText)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);
            notificationManager.notify(responseName.hashCode(), builder.build());

            Log.i(this.tag, "Notification sent for service: " + responseName);
        } else {
            Log.e(this.tag, "Notification permission not granted.");
            requestNotificationPermission();
        }
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "AUTY Notifications";
            String description = "Channel for AUTY Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(this.ChannelID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = this.context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (this.context != null) {
                ActivityCompat.requestPermissions((Activity) this.context,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_REQUEST_CODE);
            } else {
                Log.e(this.tag, "Context is not an Activity. Cannot request permission.");
            }
        }
    }

    public boolean checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }



}
