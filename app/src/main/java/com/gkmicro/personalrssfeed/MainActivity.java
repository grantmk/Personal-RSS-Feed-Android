package com.gkmicro.personalrssfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;

public class MainActivity extends AppCompatActivity {

    ArrayList<RssItem> rssItems;
    List<RssFeed> rssFeeds;
    int feedCount;
    int retrievedFeedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fab.getContext(), AddFeedActivity.class);
                startActivity(intent);
            }
        });

        rssItems = new ArrayList<RssItem>();
        rssFeeds = new Database(this).getRssFeeds();
        feedCount = rssFeeds.size();
        retrievedFeedCount = 0;


        //line to check for internet connection

        //if no connection, show user dialog that allows them to try again


        for (int i = 0; i < rssFeeds.size(); i++) {
            GetFeedItems(rssFeeds.get(i).rssFeedAddress);
        }
    }

    public void GetFeedItems (final String feedAddress) {
        try {
            SimpleRss2Parser parser = new SimpleRss2Parser(feedAddress,
                    new SimpleRss2ParserCallback() {
                        @Override
                        public void onFeedParsed (List<RSSItem> items) {
                            for (int i = 0; i < items.size(); i++) {
                                RssItem item = new RssItem();
                                item.setTitle(items.get(i).getTitle());
                                item.setDescription(items.get(i).getDescription());
                                item.setLink(items.get(i).getLink());
                                item.setPubDate(items.get(i).getDate());
                                item.setChannel(feedAddress);

                                Log.d("ITEM RECEIVED", items.get(i).getDate() + " FROM " + feedAddress);
                                rssItems.add(item);
                            }
                            PopulateListView();
                        }

                        @Override
                        public void onError(Exception ex) {
                            PopulateListView();
                        }
                    });
            parser.parseAsync();
        }
        catch (Exception e) {
            Toast.makeText(this, "Address not found: " + feedAddress, Toast.LENGTH_LONG).show();
            PopulateListView();
        }
    }

    public void PopulateListView () {
        retrievedFeedCount++;
        if(retrievedFeedCount == feedCount) {
            Log.d("FEEDS RETRIEVED", "got all feeds");
            ListView listView = (ListView) findViewById(R.id.rssFeedItemListView);
            //line to sort rssItems
            Collections.sort(rssItems, Collections.reverseOrder());
            for(int i=0; i<rssItems.size(); i++){
                Log.d("ORDERED LIST: ", rssItems.get(i).getPubDate().toString());
            }

            listView.setAdapter(new FeedItemAdapter(this, rssItems));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, RssItemViewActivity.class);
                    RssItem item = rssItems.get(position);
                    intent.putExtra("url", item.getLink().toString());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this, EditRssFeedActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_filter) {
            if(popupShowing) {
                popupShowing = false;
                popupWindow.dismiss();
                return true;
            }
            LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.filter_popup_view, null);
            popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            Button btnDismiss = (Button)popupView.findViewById(R.id.filterPopupCancel);
            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupShowing = false;
                    popupWindow.dismiss();
                }});

            popupWindow.showAsDropDown(findViewById(R.id.action_filter), -240, 30);
            popupShowing = true;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    boolean popupShowing = false;
    PopupWindow popupWindow;
}
