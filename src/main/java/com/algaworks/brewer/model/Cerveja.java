package com.algaworks.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.validation.SKU;

@Entity
@Table(name = "cerveja")
public class Cerveja implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@SKU
	@NotBlank(message = "O SKU é obrigatório!")
	private String sku;

	@NotBlank(message = "O NOME é obrigatório")
	private String nome;

	@NotBlank(message = "A descrição é obrigatória!")
	@Size(max = 50, message = "O tamanho da descrição deve ser entre 1 e 50")
	private String descricao;

	@NotNull(message = "Valor é obrigatório!")
	@DecimalMin(value = "0.01")
	@DecimalMax(value = "9999999.99", message = "O valor da cerveja deve ser menor que R$9.999.999,99")
	private BigDecimal valor;

	@NotNull(message = "O teor alcóolico é obrigatório")
	@DecimalMax(value = "100.0", message = "O valor do teor alcóolico deve ser menor que 100")
	@Column(name = "teor_alcoolico")
	private BigDecimal teorAlcoolico;

	@NotNull(message = "A comissão é obrigatória")
	@DecimalMax(value = "100.0")
	private BigDecimal comissao;

	@NotNull(message = "A quantidade de estoque é obrigatória")
	@Column(name = "quantidade_estoque")
	@Max(value = 9999, message = "A quantidade de estoque deve ser menor que 9.999")
	private Integer quantidadeEstoque;

	@NotNull(message = "A origem é obrigatória!")
	@Enumerated(EnumType.STRING)
	private Origem origem;

	@NotNull(message = "O sabor é obrigatório!")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;

	@NotNull(message = "O estilo é obrigatório!")
	@ManyToOne
	@JoinColumn(name = "codigo_estilo")
	private Estilo estilo;

	private String foto;

	@Column(name = "content_type")
	private String contentType;

	@PrePersist
	@PreUpdate
	private void prePersitUpdate() {
		sku = sku.toUpperCase();
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "cerveja-mock.png";
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}

	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Sabor getSabor() {
		return sabor;
	}

	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}

	public Estilo getEstilo() {
		return estilo;
	}

	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "Cerveja [codigo=" + codigo + ", sku=" + sku + ", nome=" + nome + ", descricao=" + descricao + ", valor=" + valor + ", teorAlcoolico="
				+ teorAlcoolico + ", comissao=" + comissao + ", quantidadeEstoque=" + quantidadeEstoque + ", origem=" + origem + ", sabor=" + sabor
				+ ", estiloCodigo=" + estilo + "]";
	}

}
