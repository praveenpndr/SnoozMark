package in.snoozmark.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

/**
 * Created by praveen on 03-05-2015.
 */
public class AlarmReciever extends BroadcastReceiver {
    NotificationManager notifManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String link = intent.getStringExtra("Link");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

        //startActivity(browserIntent);

        notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, browserIntent, 0);
        Notification noti = new Notification.Builder(context)
                .setContentTitle("SnoozMark:" + link)
                .setContentText("New snoozmark").setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent)
                .addAction(R.mipmap.ic_launcher, "Snooze", contentIntent)
                .addAction(R.mipmap.ic_launcher, "Pocket it", contentIntent).build();
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        int requestID = (int) System.currentTimeMillis();
        notifManager.notify(requestID, noti);

    }
}
