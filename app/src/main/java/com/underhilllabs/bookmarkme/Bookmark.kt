package com.underhilllabs.bookmarkme

/**
 * Data Class to store bookmark data.
 */
data class Bookmark(val title: String = "",
                    val address: String = "",
                    val user_id: Int = 1,
                    val updated_at: String = "",
                    // val tags: String,
                    val description: String = "")