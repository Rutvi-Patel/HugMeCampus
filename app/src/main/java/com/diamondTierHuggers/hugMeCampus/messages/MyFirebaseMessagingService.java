package com.diamondTierHuggers.hugMeCampus.messages;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.diamondTierHuggers.hugMeCampus.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    //    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "Token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("TAG", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Message data payload: " + remoteMessage.getData());

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String body = remoteMessage.getNotification().getBody();
            String title = remoteMessage.getNotification().getTitle();
            System.out.println("Message Title" + title);
            System.out.println("Message Body" + body);
            showNotification(title, body);
        }
    }

    public  void showNotification(String title, String data){
        String channel = "HugMeApp";
        NotificationManager n = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(channel,"HugMeNotification", NotificationManager.IMPORTANCE_HIGH );
            nc.setDescription("This is HugMeApp Notification");
            nc.enableLights(true);
            n.createNotificationChannel(nc);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channel);
        builder.setAutoCancel(true)
                .setContentText(data)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher);
        n.notify(100, builder.build());
    }

}
