package com.souzaoe.dscatalog.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

public class StandardError implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private Instant linhaTempo;
	private Integer estado; 
	private String error; 
	private String mensagem; 
	private String caminho; 
	
	public StandardError() {		
	}

	public Instant getLinhaTempo() {
		return linhaTempo;
	}

	public void setLinhaTempo(Instant linhaTempo) {
		this.linhaTempo = linhaTempo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	
}
