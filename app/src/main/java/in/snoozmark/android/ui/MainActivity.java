package in.snoozmark.android.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import in.snoozmark.android.BaseActivity;
import in.snoozmark.android.CustomListAdapter;
import in.snoozmark.android.R;
import in.snoozmark.android.database.BookMark;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends BaseActivity {
    private List<BookMark> priceList;
    private Dialog progressDialog;

    ListView list;
    String[] linkUrl ;
    String[] linkAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        adapter = new CustomListAdapter(MainActivity.this, linkUrl, linkAlarm);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    public void gotoSnooze(View view){
        Intent intent = new Intent(MainActivity.this, SnoozeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
