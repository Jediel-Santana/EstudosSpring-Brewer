package com.algaworks.brewer.repository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {

	public void preparar(Criteria criteria, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int quantidadeDeRegistros = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * quantidadeDeRegistros;

		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(quantidadeDeRegistros);

		Sort sort = pageable.getSort();

		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
	}

}
