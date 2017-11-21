package com.underhilllabs.bookmarkme

import com.google.gson.annotations.SerializedName



/**
 * Created by bart on 11/18/17.
 */
class BookmarkList {
    @SerializedName("bookmarkList")
    private var bookmarkList: ArrayList<Bookmark>? = null

    fun getBookmarkArrayList(): ArrayList<Bookmark>? {
        return bookmarkList
    }

    fun setBookmarkArrayList(bookmarkArrayList: ArrayList<Bookmark>) {
        this.bookmarkList = bookmarkArrayList
    }
}
