package in.snoozmark.android.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import in.snoozmark.android.BaseActivity;
import in.snoozmark.android.NotificationCounter;
import in.snoozmark.android.R;



public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        SharedPreferences sharedPreferences = getSharedPreferences("NOTIFICATION_COUNT", Context.MODE_APPEND);
        sharedPreferences.edit().putInt("notificationCount", 0);
        sharedPreferences.edit().putString("notificationText", "");
        sharedPreferences.edit().commit();
        */



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
