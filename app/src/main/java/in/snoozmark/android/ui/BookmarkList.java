package in.snoozmark.android.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import in.snoozmark.android.BaseActivity;
import in.snoozmark.android.CustomListAdapter;
import in.snoozmark.android.NotificationCounter;
import in.snoozmark.android.R;
import in.snoozmark.android.database.BookMark;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class BookmarkList extends BaseActivity {

    ListView list;
    String[] linkUrl ;
    String[] linkAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);

        if(getIntent().getStringExtra("caller") == "AlarmReciever"){
            NotificationCounter.setPendingNotificationsCount(0);
            NotificationCounter.setPendingNotificationText("");
            Log.d("praveen panduru", "setting shared values to 0 and null");
        }

        /*
        SharedPreferences sharedPreferences = getSharedPreferences("NOTIFICATION_COUNT", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("notificationCount", 0);
        sharedPreferences.edit().putString("notificationText", "");
        Log.d("praveen panduru", "setting shared values to 0 and null");
        sharedPreferences.edit().commit();
        */

        Realm realm = Realm.getInstance(getBaseContext());
        RealmQuery<BookMark> query = realm.where(BookMark.class);
        RealmResults<BookMark> result1 = query.findAll();

        linkUrl = new String[result1.size()];
        linkAlarm = new String[result1.size()];
        int i =0;
        for (BookMark lot : result1) {
            linkUrl[i] = lot.getLinkUrl();
            linkAlarm[i] = lot.getLinkAlarmTime();
            i++;
        }

        CustomListAdapter adapter;
        adapter = new CustomListAdapter(BookmarkList.this, linkUrl, linkAlarm);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookmark_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
