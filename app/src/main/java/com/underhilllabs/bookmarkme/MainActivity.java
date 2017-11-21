package com.underhilllabs.bookmarkme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Bookmark> data;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bookmarks.denvertech.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BookmarkmeService service = retrofit.create(BookmarkmeService.class);
        Call<List<Bookmark>> call = service.listBookmarks();
        call.enqueue(new Callback<List<Bookmark>>() {
            @Override
            public void onResponse(Call<List<Bookmark>> call, Response<List<Bookmark>> response) {

                BookmarkmeService bookmarkresponse = (BookmarkmeService) response.body();
                data = new ArrayList<Bookmark>(Arrays.asList(bookmarkresponse));
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Bookmark>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
}
