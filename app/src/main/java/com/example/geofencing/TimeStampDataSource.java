package com.example.geofencing;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TimeStampDataSource {
	
	//Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_COMMENT };
	
	public TimeStampDataSource(Context context) {
	 dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
	 database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
	 dbHelper.close();
	}
	
	public TimeStamp createComment(String comment) {
	 ContentValues values = new ContentValues();
	 values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
	 long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
	     values);
	 Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	     allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	     null, null, null);
	 cursor.moveToFirst();
	 TimeStamp newComment = cursorToComment(cursor);
	 cursor.close();
	 return newComment;
	}
	
	public void deleteComment(TimeStamp comment) {
	 long id = comment.getId();
	 System.out.println("Comment deleted with id: " + id);
	 database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
	     + " = " + id, null);
	}
	
	public List<TimeStamp> getAllComments() {
	 List<TimeStamp> comments = new ArrayList<TimeStamp>();
	
	 Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	     allColumns, null, null, null, null, null);
	
	 cursor.moveToFirst();
	 while (!cursor.isAfterLast()) {
	   TimeStamp comment = cursorToComment(cursor);
	   comments.add(comment);
	   cursor.moveToNext();
	 }
	 // make sure to close the cursor
	 cursor.close();
	 return comments;
	}
	
	private TimeStamp cursorToComment(Cursor cursor) {
	 TimeStamp comment = new TimeStamp();
	 comment.setId(cursor.getLong(0));
	 comment.setComment(cursor.getString(1));
	 return comment;
	}
} 
