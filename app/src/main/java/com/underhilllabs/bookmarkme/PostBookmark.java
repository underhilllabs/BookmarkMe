package com.underhilllabs.bookmarkme;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by bart on 11/28/15.
 */
public class PostBookmark {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String bookmarkJson(String api_user_id, String api_token, String url, String title, String desc, String tags, Boolean bool_private, Boolean bool_archived) {
        JSONObject bookmark = new JSONObject();
        int is_archived = (bool_archived) ?  1 : 0;
        int is_private =  (bool_private) ? 1 : 0;
        try {
            bookmark.put("user_id", api_user_id);
            bookmark.put("token", api_token);
            bookmark.put("url", url);
            bookmark.put("title", title);
            bookmark.put("desc", desc);
            bookmark.put("tag_list", tags);
            bookmark.put("private", is_private);
            bookmark.put("is_archived", is_archived);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return bookmark.toString();
    }
    /*
    public static void main(String[] args) throws IOException {
        PostBookmark example = new PostBookmark();
        String json = example.bowlingJson("Jesse", "Jake");
        String response = example.post("http://www.roundsapp.com/post", json);
        System.out.println(response);
    }
   */
}
