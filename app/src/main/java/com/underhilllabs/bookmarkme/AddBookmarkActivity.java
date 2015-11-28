package com.underhilllabs.bookmarkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

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
        String linkStr = etAddress.getText().toString();
        String titleStr = etTitle.getText().toString();
        String descStr = etDesc.getText().toString();
        String tagsStr = etTags.getText().toString();
        Boolean is_private = cbPrivate.isChecked();
        Boolean is_archived = cbArchive.isChecked();
    }
}
