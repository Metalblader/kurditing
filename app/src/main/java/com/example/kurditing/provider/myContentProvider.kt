package com.example.kurditing.provider

import MyDB.commentDB
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.kurditing.myDBHelper

class myContentProvider : ContentProvider() {
    private var dbHelper: myDBHelper? = null
    override fun onCreate(): Boolean {
        dbHelper = myDBHelper(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = commentDB.commentTable.TABLE_COMMENT
        var cursor : Cursor = queryBuilder.query(dbHelper?.readableDatabase, projection, selection, selectionArgs, null, null, sortOrder)
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object{
        val AUTHORITY = "com.example.kurditing.provider.provider.myContentProvider"
        val COMMENT_TABLE = commentDB.commentTable.TABLE_COMMENT
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/$COMMENT_TABLE")
    }
}