package in.snoozmark.android;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import in.snoozmark.android.database.BookMark;


/**
 * Created by Praveen Panduru on 08/07/15.
 */
public class SnoozeMarkViewAdapter extends RecyclerView.Adapter<SnoozeMarkViewAdapter.SnoozeMarkViewHolder>{

    private List<BookMark> bookMarkList;

    public SnoozeMarkViewAdapter(List<BookMark> bookMarkList) {
        this.bookMarkList = bookMarkList;
    }

    @Override
    public int getItemCount() {
        return bookMarkList.size();
    }
    @Override
    public void onBindViewHolder(SnoozeMarkViewHolder snoozeMarkViewHolder, int i) {
        BookMark bookmark = bookMarkList.get(i);
        snoozeMarkViewHolder.linkTitle.setText(bookmark.getLinkTitle());
        snoozeMarkViewHolder.linkAlarmTime.setText(bookmark.getLinkAlarmTime());
        snoozeMarkViewHolder.linkRead.setText(Integer.toString(bookmark.getLinkId()));
        snoozeMarkViewHolder.cardBellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snoozeMarkViewHolder.cardDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snoozeMarkViewHolder.cardLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public SnoozeMarkViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new SnoozeMarkViewHolder(itemView);
    }



    public static class SnoozeMarkViewHolder extends RecyclerView.ViewHolder{

        protected TextView linkTitle, linkAlarmTime, linkRead;
        ImageButton cardBellButton, cardDeleteButton, cardLikeButton;

        public SnoozeMarkViewHolder(View v){
            super(v);
            linkTitle = (TextView) v.findViewById(R.id.linkcardtitle);
            linkAlarmTime = (TextView) v.findViewById(R.id.linkcardsnoozetime);
            linkRead = (TextView) v.findViewById(R.id.linkcardread);
            cardBellButton = (ImageButton) v.findViewById(R.id.bellbutton);
            cardDeleteButton = (ImageButton) v.findViewById(R.id.deletebutton);
            cardLikeButton = (ImageButton) v.findViewById(R.id.likebutton);

        }


    }
}
