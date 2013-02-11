package com.ufc.scramble_word.bean;

import java.io.Serializable;

public class Word implements Serializable{
	
	private long id;
	private String conteudo;
	private int tamanho;
	private String dica;
	
	public String getDica(){
		return dica;
	}
	
	public void setDica(String dica){
		this.dica = dica;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	
}
