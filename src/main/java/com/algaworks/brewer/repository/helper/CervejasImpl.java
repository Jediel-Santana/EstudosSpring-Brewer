package com.algaworks.brewer.repository.helper;

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

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(cervejaFilter, criteria);

		return new PageImpl<Cerveja>(criteria.list(), pageable, total(cervejaFilter));
	}

	public long total(CervejaFilter cervejaFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(cervejaFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(CervejaFilter cervejaFilter, Criteria criteria) {
		if (Objects.nonNull(cervejaFilter)) {

			if (!StringUtils.isEmpty(cervejaFilter.getSku())) {
				criteria.add(Restrictions.eq("sku", cervejaFilter.getSku()));
			}

			if (!StringUtils.isEmpty(cervejaFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", cervejaFilter.getNome(), MatchMode.ANYWHERE));
			}

			if (isEstiloPresente(cervejaFilter)) {
				criteria.add(Restrictions.eq("estilo", cervejaFilter.getEstilo()));
			}

			if (cervejaFilter.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", cervejaFilter.getSabor()));
			}

			if (cervejaFilter.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", cervejaFilter.getOrigem()));
			}

			if (cervejaFilter.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", cervejaFilter.getValorDe()));
			}

			if (cervejaFilter.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", cervejaFilter.getValorAte()));
			}

		}
	}

	private boolean isEstiloPresente(CervejaFilter cervejaFilter) {
		return cervejaFilter.getEstilo() != null && cervejaFilter.getEstilo().getCodigo() != null;
	}

}
