package com.example.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDB extends SQLiteOpenHelper {
	private Context context;
	public NotesDB(Context context) {
		super(context, "notes", null, 4);
		this.context = context;
	}

	public static final String CRETAE_BOOK = "create table notes("
			+ "id integer primary key autoincrement," + "content text,"
			+ "time text," + "path text)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CRETAE_BOOK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists notes");
		onCreate(db);
	}

}
