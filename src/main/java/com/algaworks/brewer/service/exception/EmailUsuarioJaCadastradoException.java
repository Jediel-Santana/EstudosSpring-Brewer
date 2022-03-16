package com.algaworks.brewer.service.exception;

public class EmailUsuarioJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailUsuarioJaCadastradoException() {
		super("E-mail já cadastrado!");
	}
}
