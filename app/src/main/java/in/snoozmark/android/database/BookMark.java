package in.snoozmark.android.database;

import java.sql.Time;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by praveen on 01-07-2015.
 */
public class BookMark extends RealmObject{

    @PrimaryKey
    private int linkId = 0;

    private String linkUrl;
    private String linkAlarmTime;
    private String linkTitle;

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkAlarmTime() {
        return linkAlarmTime;
    }

    public void setLinkAlarmTime(String linkAlarmTime) {
        this.linkAlarmTime = linkAlarmTime;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

}
