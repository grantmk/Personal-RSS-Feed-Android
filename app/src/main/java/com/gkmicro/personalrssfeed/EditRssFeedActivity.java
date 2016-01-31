package com.gkmicro.personalrssfeed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditRssFeedActivity extends AppCompatActivity {

    List<RssFeed> feeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rss_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        feeds = new Database(this).getRssFeeds();
        RssFeedsAdapter adapter = new RssFeedsAdapter(this, feeds);

        final ListView listView = (ListView) findViewById(R.id.editRssListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //write an alert dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditRssFeedActivity.this);
                dialog.setTitle("Remove Feed");
                dialog.setMessage("Are you sure you want to remove this feed?");

                final int positionToRemove = position;

                dialog.setNegativeButton("No", null);
                dialog.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RssFeed selectedFeed = feeds.get(positionToRemove);
                        new Database(EditRssFeedActivity.this).deleteRssFeed(selectedFeed);

                        feeds = new Database(EditRssFeedActivity.this).getRssFeeds();
                        RssFeedsAdapter adapterNew = new RssFeedsAdapter(EditRssFeedActivity.this, feeds);

                        listView.setAdapter(adapterNew);
                    }
                });
                dialog.show();
            }
        });
    }

}
