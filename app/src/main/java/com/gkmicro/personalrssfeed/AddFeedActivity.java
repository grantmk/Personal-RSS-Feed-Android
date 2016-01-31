package com.gkmicro.personalrssfeed;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class AddFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button saveButton = (Button) findViewById(R.id.addFeedButton);
        final EditText feedAddress = (EditText) findViewById(R.id.addFeedEditText);
        final EditText feedName = (EditText) findViewById(R.id.addFeedNameEditText);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedAddress.getText().length() < 6) {
                    Toast.makeText(getApplicationContext(), "RSS Feed address is too short", Toast.LENGTH_LONG).show();
                    return;
                }
                if(feedName.getText().length() < 3) {
                    Toast.makeText(getApplicationContext(), "RSS Feed name must be longer than 3 characters", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    //check the address is valid
               //     URL url = new URL(feedAddress.getText().toString());
                 //   HttpURLConnection http = (HttpURLConnection) url.openConnection();
               //     http.setConnectTimeout(2000);
                //    int statusCode = http.getResponseCode();

                //    Log.d("STATUS CODE: ", "");
                  //  if (statusCode != 200) {
                       // Toast.makeText(getApplicationContext(), "RSS Feed not responding. Please check address.", Toast.LENGTH_LONG).show();
                        //return;
                  //  }

                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "RSS Feed not responding. Please check address.", Toast.LENGTH_LONG).show();
                    Log.d("WEB ERROR: ", e.getMessage());
                    return;
                }

                Database db = new Database(AddFeedActivity.this);

                RssFeed feed = new RssFeed (feedName.getText().toString(), feedAddress.getText().toString());

                db.addRssFeed(feed);
                Toast.makeText(getApplicationContext(), "Rss Feed Added!", Toast.LENGTH_LONG).show();
                feedAddress.setText("");
                feedName.setText("");
            }
        });
    }

}
