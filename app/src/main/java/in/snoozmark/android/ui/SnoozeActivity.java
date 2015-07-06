package in.snoozmark.android.ui;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import in.snoozmark.android.AlarmReciever;
import in.snoozmark.android.BaseActivity;
import in.snoozmark.android.DateUtils;
import in.snoozmark.android.MaterialSpinner;
import in.snoozmark.android.R;
import in.snoozmark.android.database.BookMark;
import in.snoozmark.android.textclockbackport.TextClock;
import io.realm.Realm;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import in.snoozmark.android.MaterialSpinner;

public class SnoozeActivity extends BaseActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener
{
    TextView webTitle;
    TextView webLink;
    NumberPicker minPicker;
    String sharedText = "";
    String sharedTitle = "";
    String localSharedText = "";
    TextView datenum, dateam_pm, datetext;
    private static final String[] ITEMS = {"Morning 9:00AM", "Afternoon 1:00PM", "Evening 5:00PM", "Night 9:00PM", "After sometime..", "Pick a time.."};
    private ArrayAdapter<String> adapter;
    MaterialSpinner spinner1;
    static Dialog d ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        setTitle("SnoozMark");
        webTitle = (TextView) findViewById(R.id.sharedLink);
        webLink = (TextView) findViewById(R.id.weblink);
        datenum = (TextView) findViewById(R.id.timenum);
        dateam_pm = (TextView) findViewById(R.id.AM_PM);
        datetext = (TextView) findViewById(R.id.date_text);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setDisplayTime(9);
                } else if (position == 1) {
                    setDisplayTime(13);
                } else if (position == 2) {
                    setDisplayTime(17);
                } else if (position == 3) {
                    setDisplayTime(21);
                    Log.d("praveen panduru", "its in 9pm");
                } else if (position == 4) {
                    showNumPickerDialog();
                } else if (position == 5) {
                    datenum.performClick();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        setTimeAndDate(0,0);

        datenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        SnoozeActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setThemeDark(true);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");

            }
        });

        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SnoozeActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(true);
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });



        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    void showNumPickerDialog(){
        final Dialog d = new Dialog(SnoozeActivity.this);
        d.setTitle("Snooze for..");
        d.setContentView(R.layout.numpickerdialog);
        TextView cancel = (TextView) d.findViewById(R.id.button2);
        TextView done = (TextView) d.findViewById(R.id.button1);
        final NumberPicker hourPicker = (NumberPicker) d.findViewById(R.id.numberPicker1);
        final NumberPicker minPicker = (NumberPicker) d.findViewById(R.id.numberPicker2);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(12);
        hourPicker.setValue(0);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(60);
        minPicker.setValue(30);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                setTimeAndDate(hourPicker.getValue(), minPicker.getValue());


            }
        });
        d.show();
    }

    void setDisplayTime(int hourint){
        Calendar caltoday = Calendar.getInstance();
        int hour = caltoday.get(Calendar.HOUR_OF_DAY);
        if(hour>=hourint){
            datetext.setText("TOMORROW");
        }
        else
            datetext.setText("TODAY");
        String am_pm;
        int hourValue;
        if(hourint>=12) {
            am_pm = "PM";
            if (hourint>12)
                hourValue = hourint - 12;
            else
                hourValue = hourint;
        }
        else {
            am_pm = "AM";
            hourValue = hourint;
        }

        datenum.setText(hourValue + ":00");
        dateam_pm.setText(am_pm);
    }


    public void setTimeAndDate(int hours, int mins){
        Long time = new GregorianCalendar().getTimeInMillis()+ hours*60*60*1000 + mins*60*1000;
        Calendar cal = Calendar.getInstance();
        Calendar caltoday = Calendar.getInstance();


        cal.setTimeInMillis(time);
        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);

        Log.d("praveen panduru", "time is " + hour + min);
        String AM_PM;
        if(cal.get(Calendar.HOUR_OF_DAY) < 12){
            AM_PM = "AM";
        }
        else{
            AM_PM = "PM";
        }

        datenum.setText(hour + ":" + min);
        dateam_pm.setText(AM_PM);
        if(DateUtils.isSameDay(caltoday, cal))
            datetext.setText("TODAY");
        else
            datetext.setText("TOMORROW");

    }
    void setTime(int hourOfDay, int min){
        Calendar caltoday = Calendar.getInstance();
        int hour = caltoday.get(Calendar.HOUR_OF_DAY);
        int minutes = caltoday.get(Calendar.MINUTE);
        String day, am_pm;
        int hourValue;
        if(hour < hourOfDay)
            day = "TODAY";
        else if (hour == hourOfDay){
            if (minutes < min)
                day = "TODAY";
            else
                day = "TOMORROW";
        }
        else
            day = "TOMORROW";
        if(hourOfDay>=12) {
            am_pm = "PM";
            if (hourOfDay>12)
                hourValue = hourOfDay - 12;
            else
                hourValue = hourOfDay;
        }
        else {
            am_pm = "AM";
            hourValue = hourOfDay;
        }
        if(datetext.getText() == "TODAY" || datetext.getText() == "TOMORROW")
            datetext.setText(day);
        dateam_pm.setText(am_pm);
        datenum.setText(hourValue + ":" + min);

    }

    void setDate(int year, int month, int day){
        Calendar caltoday = Calendar.getInstance();
        Calendar caldate = Calendar.getInstance();
        int hours = Integer.parseInt(datenum.getText().toString().split(":")[0]);
        int minutes = Integer.parseInt(datenum.getText().toString().split(":")[1]);
        if (dateam_pm.getText() == "PM"){
            if (hours != 12)
               hours = hours +12;
        }
        String dayString;
        caldate.set(year, month, day, hours, minutes);
        String months[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        if (DateUtils.isSameDay(caldate, caltoday))
            dayString = "TODAY";
        else if (DateUtils.isWithinDaysFuture(caldate,1))
            dayString = "TOMORROW";
        else
            dayString = day + " " +months[month-1] + " " + year;
        datetext.setText(dayString);

    }

    void handleSendText(Intent intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        sharedTitle = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            webTitle.setText(sharedTitle);
            webLink.setText(sharedText);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        setDate(year, monthOfYear, dayOfMonth);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);

    }
}
