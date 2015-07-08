package in.snoozmark.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.snoozmark.android.R;
import in.snoozmark.android.SnoozeMarkViewAdapter;
import in.snoozmark.android.database.BookMark;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by neokree on 16/12/14.
 */
public class FragmentText extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        TextView text = new TextView(container.getContext());
        text.setText("Fragment content");
        text.setGravity(Gravity.CENTER);
        */

        View dataView = inflater.inflate(R.layout.bookmarkcardlist, container, false);

        RecyclerView recList = (RecyclerView) dataView.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        SnoozeMarkViewAdapter snoozemarkAdapter = new SnoozeMarkViewAdapter(getList());
        recList.setAdapter(snoozemarkAdapter);

        return dataView;
    }

    private List<BookMark> getList(){
        Realm realm = Realm.getInstance(getActivity());
        RealmQuery<BookMark> query = realm.where(BookMark.class);
        RealmResults<BookMark> result1 = query.findAll();

        return result1;
    }
}


