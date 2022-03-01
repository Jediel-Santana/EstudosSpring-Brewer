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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLogradouro(String logradouro) {
		Logradouro = logradouro;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Endereco [Logradouro=" + Logradouro + ", numero=" + numero + ", complemento=" + complemento + ", cep="
				+ cep + ", cidade=" + cidade + ", estado=" + estado + "]";
	}
	
}
