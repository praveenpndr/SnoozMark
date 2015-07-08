package in.snoozmark.android;

import android.app.Application;

/**
 * Created by Praveen Panduru on 07/07/15.
 */
public class NotificationCounter extends Application{

    private static int pendingNotificationsCount = 0;
    private static String pendingNotificationText = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static int getPendingNotificationsCount() {
        return pendingNotificationsCount;
    }

    public static void setPendingNotificationsCount(int pendingNotifications) {
        pendingNotificationsCount = pendingNotifications;
    }

    public static String getPendingNotificationText() {
        return pendingNotificationText;
    }

    public static void setPendingNotificationText(String pendingNotificationText) {
        NotificationCounter.pendingNotificationText = pendingNotificationText;
    }

}
