package com.ufc.scramble_word.database;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseController {
	private SQLiteDatabase db;
	private DatabaseHelper helper;
	
	public DatabaseController(Context context){
		helper = new DatabaseHelper(context);
		db = helper.getDatabase();
	}
	
	public List<JSONObject> allPalavras() {
	    List<JSONObject> result = new ArrayList<JSONObject>();
	    
	    Cursor cursor = db.query("palavras", new String[]{"id","palavra","dica","nivel"}, null, null, null, null, "id ASC");
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()){
	    	JSONObject obj = new JSONObject();
	    	try{
	    		obj.put("id", cursor.getInt(0));
	            obj.put("palavra",cursor.getString(1));
	            obj.put("dica",cursor.getString(2));
	            obj.put("nivel", cursor.getString(3));
	    	}catch (JSONException e){
	    		
	    	}
	    	result.add(obj);
	    	
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    return result;
	}
	
	public JSONObject randomPalavra(){
		
		Cursor cursor = db.query("palavras", new String[]{"id","palavra","dica","nivel"}, "id = 1", null, null, null, "id ASC");
		
		JSONObject obj = new JSONObject();
		try{
    		obj.put("id", cursor.getInt(0));
            obj.put("palavra",cursor.getString(1));
            obj.put("dica",cursor.getString(2));
            obj.put("nivel", cursor.getString(3));
		}catch(JSONException e){
			
		}
		cursor.close();
		return obj;
	}
}
