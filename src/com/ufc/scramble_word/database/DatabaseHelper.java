package com.ufc.scramble_word.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private final static String NOME_DB = "word_db";
	private final static int VERSAO_DB = 1;

	public final static String NOME_TABELA = "word";

	public final static String WORD_ID = "id";
	public final static String WORD_CONTEUDO = "conteudo";
	public final static String WORD_TAMANHO = "tamanho";
	public final static String WORD_DICA = "dica";

	// public final static String

	public DatabaseHelper(Context contexto) {
		super(contexto, NOME_DB, null, VERSAO_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String sql = "";
		sql += "CREATE TABLE " + NOME_TABELA + " ( "
				+ "	    id            INTEGER PRIMARY KEY AUTOINCREMENT "
				+ "	                          NOT NULL"
				+ "	                          UNIQUE,"
				+ "	    conteudo       VARCHAR NOT NULL,"
				+ "	    dica       VARCHAR NOT NULL,"
				+ "	    tamanho        INTEGER NOT NULL);";

		Log.d("DBHelper.onCreate", "Criação da Tabela");
		
		db.execSQL(sql);
		
		ContentValues values = new ContentValues();
	    
		/* Inserindo dados */
	    values.put("conteudo", "cachorro");
	    values.put("tamanho", 8);
	    values.put("dica", "Animal");
	    db.insert(NOME_TABELA, null, values);
	    
	    values.put("conteudo", "java");
	    values.put("tamanho", 4);
	    values.put("dica", "Linguagem de Programação");
	    db.insert(NOME_TABELA, null, values);
	    
	    values.put("conteudo", "android");
	    values.put("tamanho", 7);
	    values.put("dica", "Sistema Operacional para Dispositivos Móveis");
	    db.insert(NOME_TABELA, null, values);
	    
	    values.put("conteudo", "ornitorrinco");
	    values.put("tamanho", 12);
	    values.put("dica", "Mamífero semiaquático");
	    db.insert(NOME_TABELA, null, values);
	    
	    values.put("conteudo", "paralelepipedo");
	    values.put("tamanho", 14);
	    values.put("dica", "Pedra de calçamento");
	    db.insert(NOME_TABELA, null, values);
	    
	    values.put("conteudo", "gato");
	    values.put("tamanho", 4);
	    values.put("dica", "Animal");
	    db.insert(NOME_TABELA, null, values);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("DBHelper.onUpgrade","Atualizando o Banco de Dados");

	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		Log.d("DBHelper.getWritableDatabase"," Carregando o Banco de Dados");
		return super.getWritableDatabase();
	}
}