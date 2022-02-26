package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class Endereco implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String Logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "codigo_cidade")
	private Cidade cidade;
	
	@Transient
	private Estado estado;
	
	public String getLogradouro() {
		return Logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getCep() {
		return cep;
	}

	public Cidade getCidade() {
		return cidade;
	}	
	

	public Estado getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Endereco [Logradouro=" + Logradouro + ", numero=" + numero + ", complemento=" + complemento + ", cep="
				+ cep + ", cidade=" + cidade + "]";
	}
}
