package com.example.culinarycompanion.util

import android.content.Context

object SessionManager {
    private const val PREF = "session"
    private const val KEY_USER = "user_id"

    fun login(c: Context, id: Long) =
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .edit().putLong(KEY_USER, id).apply()

    fun logout(c: Context) =
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .edit().remove(KEY_USER).apply()

    fun currentUserId(c: Context): Long =
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .getLong(KEY_USER, -1L)
}