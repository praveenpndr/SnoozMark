package in.snoozmark.android.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import in.snoozmark.android.AlarmReciever;
import in.snoozmark.android.BaseActivity;
import in.snoozmark.android.R;
import in.snoozmark.android.database.BookMark;
import io.realm.Realm;

public class SnoozeActivity extends BaseActivity {
    TextView link;
    NumberPicker minPicker;
    String sharedText = "";
    String localSharedText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        setTitle("SnoozMark");
        link = (TextView) findViewById(R.id.sharedLink);
        minPicker = (NumberPicker) findViewById(R.id.minPicker);
        minPicker.setMaxValue(60);
        minPicker.setMinValue(1);
        minPicker.setValue(1);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    void handleSendText(Intent intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            link.setText(sharedText);
            localSharedText = sharedText;

        }
    }

    public void setSnooze(View view){
        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
        Integer minutes = minPicker.getValue();
        Long time = new GregorianCalendar().getTimeInMillis()+ minutes*1000;

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(this, AlarmReciever.class);
        intentAlarm.putExtra("Link",sharedText);

        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int requestID = (int) System.currentTimeMillis();

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this, requestID, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm Scheduled for " + minPicker.getValue() + "minutes", Toast.LENGTH_LONG).show();

        Realm realm = Realm.getInstance(getBaseContext());
        realm.beginTransaction();
        BookMark newLink = realm.createObject(BookMark.class);
        newLink.setLinkId((int)realm.where(BookMark.class).maximumInt("linkId") + 1);
        newLink.setLinkUrl(localSharedText);

        Date d = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        newLink.setLinkAlarmTime(format.format(d));
        realm.commitTransaction();

        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_snooze, menu);
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
