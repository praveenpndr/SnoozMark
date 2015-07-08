package in.snoozmark.android.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class BookmarkList extends BaseActivity implements MaterialTabListener{

    ListView list;
    String[] linkUrl, linkAlarm, linkTitle ;
    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);


        tabHost = (MaterialTabHost) this.findViewById(R.id.materialTabHost);
        pager = (ViewPager) this.findViewById(R.id.pager);

        // init view pager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }

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

        Realm realm = Realm.getInstance(getBaseContext());
        RealmQuery<BookMark> query = realm.where(BookMark.class);
        RealmResults<BookMark> result1 = query.findAll();

        linkUrl = new String[result1.size()];
        linkAlarm = new String[result1.size()];
        linkTitle = new String[result1.size()];

        int i =0;
        for (BookMark lot : result1) {
            linkUrl[i] = lot.getLinkUrl();
            linkAlarm[i] = lot.getLinkAlarmTime();
            linkTitle[i] = lot.getLinkTitle();
            i++;
        }

        CustomListAdapter adapter;
        adapter = new CustomListAdapter(BookmarkList.this, linkTitle, linkAlarm);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        */
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

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        pager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public Fragment getItem(int num) {
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "RECENT";
            else if(position ==1)
                return "ALL";
            else
                return "FAVOURITE";
        }

    }
}
