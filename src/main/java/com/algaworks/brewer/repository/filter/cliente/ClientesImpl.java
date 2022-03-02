package com.algaworks.brewer.repository.filter.cliente;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class ClientesImpl implements ClientesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked" })
	@Override
	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);

		paginacaoUtil.preparar(criteria, pageable);
		aplicarFiltro(clienteFilter, criteria);

		return new PageImpl<Cliente>(criteria.list(), pageable, total(clienteFilter));
	}

	public Long total(ClienteFilter clienteFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		aplicarFiltro(clienteFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void aplicarFiltro(ClienteFilter clienteFilter, Criteria criteria) {
		if (Objects.nonNull(clienteFilter)) {
			if (!StringUtils.isEmpty(clienteFilter.getNome()))
				criteria.add(Restrictions.ilike("nome", clienteFilter.getNome(), MatchMode.ANYWHERE));

			if (!StringUtils.isEmpty(clienteFilter.getCpfOuCnpj()))
				criteria.add(Restrictions.ilike("cpfOuCnpj", clienteFilter.getCpfOuCnpj(), MatchMode.ANYWHERE));
		}
	}

}
