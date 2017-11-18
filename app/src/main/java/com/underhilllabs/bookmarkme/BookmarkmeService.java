package com.underhilllabs.bookmarkme;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BookmarkmeService {
    @GET("api/bookmarks/recent.json")
    Call<List<Bookmark>> listBookmarks();
}