package com.underhilllabs.bookmarkme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

public class AddBookmarkActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etAddress;
    EditText etDesc;
    EditText etTags;
    CheckBox cbPrivate;
    CheckBox cbArchive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        etTitle = (EditText)findViewById(R.id.titleText);
        etAddress = (EditText)findViewById(R.id.addressText);
        etDesc = (EditText)findViewById(R.id.descriptionText);
        etTags = (EditText)findViewById(R.id.tagsText);
        cbPrivate = (CheckBox)findViewById(R.id.checkBoxPrivate);
        cbArchive = (CheckBox)findViewById(R.id.checkBoxArchive);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(this, SettingsActivity.class));
                //return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    void handleSendText(Intent intent) {
        String linkStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        String titleStr = intent.getStringExtra(Intent.EXTRA_SUBJECT);

        if (linkStr != null) {
            // Update UI to reflect text being shared
            etAddress.setText(linkStr);
            etTitle.setText(titleStr);
        }
    }

    public void postBookmark(View view) {
        // Get preferences for API location and User Auth
        //final String api_url = "http://bkmark.me/api/posts/add.json";
        String api_user_id = "";
        String api_token = "";

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final String api_url = sharedPref.getString("api_url", "http://bkmark.me") + "/api/posts/add.json";
        api_user_id = sharedPref.getString("api_user_id", "oopsie");
        api_token = sharedPref.getString("api_user_token", "oops");
        Toast.makeText(this, "Got shared pref" + api_token, Toast.LENGTH_LONG);
        String linkStr = etAddress.getText().toString();
        String titleStr = etTitle.getText().toString();
        String descStr = etDesc.getText().toString();
        String tagsStr = etTags.getText().toString();
        Boolean is_private = cbPrivate.isChecked();
        Boolean is_archived = cbArchive.isChecked();

        final PostBookmark bk = new PostBookmark();
        final String json = bk.bookmarkJson(api_user_id, api_token, linkStr, titleStr, descStr, tagsStr, is_private, is_archived);
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    String response = bk.post(api_url, json);

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return null;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //Log.e("ANSWER", "" + s);
                finish();
            }

        }.execute();

    }
}
