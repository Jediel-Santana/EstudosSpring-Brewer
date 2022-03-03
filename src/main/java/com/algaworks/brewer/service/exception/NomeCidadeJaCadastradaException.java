package com.algaworks.brewer.service.exception;

public class NomeCidadeJaCadastradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public NomeCidadeJaCadastradaException() {
		super("Nome da cidade já cadastrado");
	}
	
}
