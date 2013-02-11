package com.ufc.scramble_word.database;

import java.util.ArrayList;

import com.ufc.scramble_word.bean.Word;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseController {

	private Context context;
	private Cursor cursor;
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	public DatabaseController(Context context) {
		this.context = context;
	}

	public void inserir(Word word) {

		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();


		values.put(DatabaseHelper.WORD_CONTEUDO ,word.getConteudo());
		values.put(DatabaseHelper.WORD_TAMANHO ,word.getTamanho());
		values.put(DatabaseHelper.WORD_DICA, word.getDica());

		db.insert(DatabaseHelper.NOME_TABELA, null, values);

		db.close();

		Log.d("ControladorDB","Inserindo Palavra");

	}

	public Word selecionar(long id) {

		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();

		Word word = new Word();
		cursor = db.query(DatabaseHelper.NOME_TABELA, new String[]{ 
			DatabaseHelper.WORD_ID,
			DatabaseHelper.WORD_CONTEUDO,
			DatabaseHelper.WORD_DICA,
			DatabaseHelper.WORD_TAMANHO }, DatabaseHelper.WORD_ID + "=" + id, null, null, null, null);


		if ( cursor == null ) {
			Log.d("ControladorDB","Id n‹o encontrado");

			cursor.close();
			db.close();

			return null;
		} else {
			cursor.moveToFirst();

			word.setId( 		cursor.getLong(cursor.getColumnIndex(DatabaseHelper.WORD_ID)) );
			word.setConteudo( 	cursor.getString(cursor.getColumnIndex(DatabaseHelper.WORD_CONTEUDO)) );
			word.setDica( 	cursor.getString(cursor.getColumnIndex(DatabaseHelper.WORD_DICA)) );
			word.setTamanho( cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WORD_TAMANHO)) );
			
			cursor.close();
			db.close();
			return word;

		}

	}

	public ArrayList<Word> selecionarTodos(){

		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();

		cursor = db.query(DatabaseHelper.NOME_TABELA, new String[]{ 
				DatabaseHelper.WORD_ID,
				DatabaseHelper.WORD_CONTEUDO,
				DatabaseHelper.WORD_DICA,
				DatabaseHelper.WORD_TAMANHO }, null, null, null, null, null);

		cursor.moveToFirst();
		ArrayList<Word> lista = new ArrayList<Word>();

		while ( !cursor.isAfterLast() ){

			Word word = new Word();

			word.setId( cursor.getLong(cursor.getColumnIndex(DatabaseHelper.WORD_ID)));
			word.setConteudo( cursor.getString( cursor.getColumnIndex(DatabaseHelper.WORD_CONTEUDO)) ) ;
			word.setDica( cursor.getString( cursor.getColumnIndex(DatabaseHelper.WORD_DICA)) ) ;
			word.setTamanho( cursor.getInt( cursor.getColumnIndex(DatabaseHelper.WORD_TAMANHO) ) );

			lista.add(word);

			cursor.moveToNext();
		}

		cursor.close();
		db.close();

		return lista;

	}

	public ArrayList<Word> selecionarPalavrasDeTamanhoAte(int tamanho) {

		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();

		cursor = db.query(DatabaseHelper.NOME_TABELA, new String[]{ 
				DatabaseHelper.WORD_ID,
				DatabaseHelper.WORD_CONTEUDO,
				DatabaseHelper.WORD_DICA,
				DatabaseHelper.WORD_TAMANHO }, DatabaseHelper.WORD_TAMANHO + "<= "+tamanho, null, null, null, null);
							//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

		cursor.moveToFirst();
		ArrayList<Word> lista = new ArrayList<Word>();

		while ( !cursor.isAfterLast() ){

			Word word = new Word();

			word.setId(		cursor.getLong	(cursor.getColumnIndex(DatabaseHelper.WORD_ID)));
			word.setConteudo(	cursor.getString(cursor.getColumnIndex(DatabaseHelper.WORD_CONTEUDO)));
			word.setDica(	cursor.getString(cursor.getColumnIndex(DatabaseHelper.WORD_DICA)));
			word.setTamanho(	cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WORD_TAMANHO)));

			lista.add(word);

			cursor.moveToNext();
		}

		cursor.close();
		db.close();

		return lista;

	}

}