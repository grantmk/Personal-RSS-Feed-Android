package com.gkmicro.personalrssfeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grant on 23/11/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rssFeeds";

    private static final String TABLE_FEEDS = "feeds";

    private static final String KEY_ID = "id";
    private static final String KEY_FEEDTITLE = "title";
    private static final String KEY_FEEDADDRESS = "address";

    public Database (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FEEDS_TABLE = "CREATE TABLE " + TABLE_FEEDS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FEEDTITLE + " TEXT," + KEY_FEEDADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_FEEDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDS);

        //create table again
        onCreate(db);
    }

    public void addRssFeed (RssFeed feed){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FEEDTITLE, feed.rssFeedTitle);
        values.put(KEY_FEEDADDRESS, feed.rssFeedAddress);

        db.insert(TABLE_FEEDS, null, values);
        db.close();
    }

    public List<RssFeed> getRssFeeds () {
        List<RssFeed> results = new ArrayList<RssFeed>();

        String selectQuery = "SELECT * FROM " + TABLE_FEEDS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                RssFeed rssFeed = new RssFeed(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                results.add(rssFeed);
            } while (cursor.moveToNext());
        }

        db.close();
        return results;
    }

    public void deleteRssFeed (RssFeed feed) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FEEDS, KEY_ID + " = ? ", new String[] {String.valueOf(feed.id)});
        db.close();
    }
}
