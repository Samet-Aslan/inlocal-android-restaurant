package com.inlocal.restaurantapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.ui.auth.splash.view.SplashActivity;
import com.inlocal.restaurantapp.util.Constants;


import org.json.JSONObject;

import java.util.Map;

public class PushMessagingService extends FirebaseMessagingService {

    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    //private static final String KEY_INFO = "info";
    private static final String KEY_TYPE = "module_type";
    private static final String KEY_REDI_ID = "redirection_id";
    private static final String KEY_DATA = "data";

    private NotificationManager notificationManager;
    private int notificationCounter = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("////", "" + remoteMessage.getData());
        Map<String, String> data = remoteMessage.getData();
        Log.d("push_notification", data.toString());

        if (data != null && data.containsKey(KEY_TITLE) && data.containsKey(KEY_BODY)) {
            String redirectId=null;
            String moduleType=null;

            if (remoteMessage.getData().containsKey(KEY_TYPE)) {
                redirectId = remoteMessage.getData().get(KEY_REDI_ID);
                moduleType = remoteMessage.getData().get(KEY_TYPE);

            }

            String title = data.get(KEY_TITLE);
            String body = data.get(KEY_BODY);

            Intent intent = new Intent(this, SplashActivity.class);

            if (moduleType != null) {
                intent.putExtra(Constants.IntentKey.RedirectionTarget, moduleType);
                intent.putExtra(Constants.IntentKey.RedirectionExtra, redirectId);
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    2000/*Constants.RequestCode.NotificationRedirection*/, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                    "Restaurunt")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND
                            | NotificationCompat.DEFAULT_LIGHTS
                            | NotificationCompat.DEFAULT_VIBRATE)
                    .setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("Restaurunt",
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(notificationCounter++, builder.build());

        }

    }

}
