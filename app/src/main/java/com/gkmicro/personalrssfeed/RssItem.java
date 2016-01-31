package com.gkmicro.personalrssfeed;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by grant on 25/11/2015.
 */
public class RssItem implements Comparable<RssItem> {

    private String channel;
    private String title;
    private String description;
    private URL link;
    private Date pubDate;

    public RssItem (String title, String description, URL link, String channel) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.channel = channel;
    }

    public RssItem () {

    }

    public void setTitle (String title) {
        this.title = title;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public void setLink (URL link) {
        this.link = link;
    }

    public void setChannel (String channel) {this.channel = channel; }

    public void setPubDate (Date pubDate) {this.pubDate = pubDate; }

    public void setPubDate (String pubDate) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        try {
            Date date = format.parse(pubDate);
            this.pubDate = date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getTitle () {
        return title;
    }

    public String getDescription () {
        return description;
    }

    public URL getLink () {
        return link;
    }

    public String getChannel () {
        return channel;
    }

    public Date getPubDate () {
        return pubDate;
    }

    @Override
    public int compareTo(RssItem another) {
        if(getPubDate() != null && another.getPubDate() != null) {
            return getPubDate().compareTo(another.getPubDate());
        } else {
            return 0;
        }
    }

}
