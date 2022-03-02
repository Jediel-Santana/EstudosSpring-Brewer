package com.algaworks.brewer.service;

public class CpfOuCpnjClienteJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CpfOuCpnjClienteJaCadastradoException() {
		super("CPF/CNPJ já cadastrado");
	}
}
