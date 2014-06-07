package com.invention.planner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	
	public static final String TASK_TABLE_NAME = "task";
	
	public TaskDbHelper(Context context, String name, CursorFactory factory) {
		super(context, name, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}
	
	private void createTable(SQLiteDatabase db) {
		String qs = "CREATE TABLE " + TASK_TABLE_NAME + " (taskID INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, dateCompleted TEXT);" ;
		db.execSQL(qs);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME + ";");
		createTable(db);
	}

}
