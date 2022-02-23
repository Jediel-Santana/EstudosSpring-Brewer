package com.algaworks.brewer.controller.page;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
		this.page = page;
		String url = httpServletRequest.getRequestURL().append(
				httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "").toString().replaceAll("\\+", "%20");
		this.uriBuilder = ServletUriComponentsBuilder.fromHttpUrl(url);
	}

	public List<T> getConteudo() {
		return page.getContent();
	}

	public boolean isVazia() {
		return page.getContent().isEmpty();
	}

	public String urlParaPagina(int pagina) {
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toString();
	}

	public boolean isPrimeira() {
		return page.isFirst();
	}

	public boolean isUltima() {
		return page.isLast();
	}

	public int getTotal() {
		return page.getTotalPages();
	}

	public int getAtual() {
		return page.getNumber();
	}

	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriBuilder.toUriString());
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
		return builder.replaceQueryParam("sort", valorSort).build(true).encode().toString();
	}

	private Object inverterDirecao(String propriedade) {
		String direcao = "asc";

		Order order = Objects.nonNull(page.getSort()) ? page.getSort().getOrderFor(propriedade) : null;

		if (Objects.nonNull(order)) {
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}

		return direcao;
	}

	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}

	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;

		if (order == null)
			return false;

		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}

}
