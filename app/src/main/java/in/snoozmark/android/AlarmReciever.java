package in.snoozmark.android;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.util.List;

import in.snoozmark.android.ui.BookmarkList;
import in.snoozmark.android.ui.MainActivity;
import in.snoozmark.android.NotificationCounter;

/**
 * Created by praveen on 03-05-2015.
 */
public class AlarmReciever extends BroadcastReceiver {
    NotificationManager notifManager, mNotificationManager;
    int notificationCount;
    public static int notificationID = 456;

    @Override
    public void onReceive(Context context, Intent intent) {
        String link = intent.getStringExtra("Link");
        String linkTitle = intent.getStringExtra("LinkTitle");
        String notifContentTitle, notifContentText;

        //startActivity(browserIntent);

        int pendingNotificationsCount = NotificationCounter.getPendingNotificationsCount();
        String pendingNotificationText = NotificationCounter.getPendingNotificationText();

        //SharedPreferences sharedPreferences = context.getSharedPreferences("NOTIFICATION_COUNT", Context.MODE_MULTI_PROCESS);
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //int pendingNotificationsCount = sharedPreferences.getInt("notificationCount", 0);
        //String pendingNotificationText = sharedPreferences.getString("notificationText", "");

        Log.d("praveen panduru", "retrived shared values - "+pendingNotificationsCount +" "+ pendingNotificationText);

        if (pendingNotificationsCount == 0){
            notifContentTitle = "New snoozmark";
            notifContentText = linkTitle;
        }
        else{
            notifContentTitle = (pendingNotificationsCount + 1) + " new snoozmarks";
            notifContentText = pendingNotificationText+"\n"+linkTitle;

        }
        String [] notifContentTextList = notifContentText.split("\n");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle(notifContentTitle)
                        .setContentText(notifContentText)
                        .setDefaults(Notification.DEFAULT_ALL);
// Creates an explicit intent for an Activity in your app
        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        Intent resultIntent = new Intent(context, BookmarkList.class);
        resultIntent.putExtra("caller", "AlarmReciever");
        Intent snoozeIntent = new Intent(context, MainActivity.class);
        Intent pocketIntent = new Intent(context, MainActivity.class);
        PendingIntent pSnoozeIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis()/1000, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pPocketIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis()/1000, pocketIntent, PendingIntent.FLAG_UPDATE_CURRENT);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(BookmarkList.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationCompat.InboxStyle inboxStyleNotif = new NotificationCompat.InboxStyle();
        if(notifContentTextList.length > 5){
            for (int i=0; i < 4; i++){
                inboxStyleNotif.addLine(notifContentTextList[i]);
                String summary = "+" +(notifContentTextList.length - 4) + " more";
                inboxStyleNotif.setSummaryText(summary);
            }
        }
        else{
            for (int i=0; i < notifContentTextList.length; i++){
                inboxStyleNotif.addLine(notifContentTextList[i]);
            }
        }


        mBuilder.setStyle(inboxStyleNotif);
        mBuilder.addAction(R.mipmap.ic_launcher, "Snooze", pSnoozeIntent);
        mBuilder.addAction(R.mipmap.ic_launcher, "Pocket it", pPocketIntent);
        pendingNotificationsCount = pendingNotificationsCount + 1;
        Log.d("praveen panduru", "incremented pendingNotificationsCount: " + pendingNotificationsCount);

        NotificationCounter.setPendingNotificationsCount(pendingNotificationsCount);
        NotificationCounter.setPendingNotificationText(notifContentText);

        //sharedPreferences.edit().putString("notificationText", notifContentText);
        //sharedPreferences.edit().putInt("notificationCount", pendingNotificationsCount);
        //sharedPreferences.edit().commit();
        //int pendingNotificationsCount1 = sharedPreferences.getInt("notificationCount", 0);
        //String pendingNotificationText1 = sharedPreferences.getString("notificationText", "");

        Log.d("praveen panduru", "retrived one shared values - " + NotificationCounter.getPendingNotificationsCount() + " " + NotificationCounter.getPendingNotificationText());


        NotificationManager mNotificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.


        mNotificationManager.notify(notificationID, mBuilder.build());

    }


}
