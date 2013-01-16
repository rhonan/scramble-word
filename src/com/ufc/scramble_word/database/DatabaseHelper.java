package com.ufc.scramble_word.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context){
		super(context, "palavras.sqlite", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE palavras(" +
                "id INT AUTO_INCREMENT," +
                "palavra VARCHAR(100)," +
                "dica VARCHAR(200)," +
                "nivel VARCHAR(10)," +
                "primary key(id));");

		ContentValues values = new ContentValues();
		
	    values.put("palavra", "RUBY");
	    values.put("dica", "Jewel");
	    values.put("nivel", "easy");
	    db.insert("palavras", null, values);
	    
	    values.put("palavra", "JAVA");
	    values.put("dica", "Programming language");
	    values.put("nivel", "easy");
	    db.insert("palavras", null, values);
	    
	    values.put("palavra", "FACEBOOK");
	    values.put("dica", "Social network");
	    values.put("nivel", "normal");
	    db.insert("palavras", null, values);
	    
	    values.put("palavra", "SHOPPING");
	    values.put("dica", "Womens likes to...");
	    values.put("nivel", "hard");
	    db.insert("palavras", null, values);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public SQLiteDatabase getDatabase(){
		return this.getWritableDatabase();
	}
	
}
