package com.gkmicro.personalrssfeed;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by grant on 23/11/2015.
 */
public class RssFeed {

    public int id;
    public String rssFeedTitle;
    public String rssFeedAddress;

    public RssFeed (String title, String link) {
        this.rssFeedTitle = title;
        this.rssFeedAddress = link;
    }

    public RssFeed (int id, String title, String link) {
        this.rssFeedTitle = title;
        this.rssFeedAddress = link;
        this.id = id;
    }

}
