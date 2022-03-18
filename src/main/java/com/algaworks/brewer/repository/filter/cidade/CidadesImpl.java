package com.algaworks.brewer.repository.filter.cidade;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class CidadesImpl implements CidadesQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		aplicarFiltro(cidadeFilter, criteria);
		criteria.createAlias("estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		
		return new PageImpl<Cidade>(criteria.list(), pageable, total(cidadeFilter));
	}
	
	
	private Long total(CidadeFilter cidadeFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		aplicarFiltro(cidadeFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	public void aplicarFiltro(CidadeFilter cidade, Criteria criteria) {
		if(Objects.nonNull(cidade)) {
			if(!StringUtils.isEmpty(cidade.getNome())) {
				criteria.add(Restrictions.ilike("nome", cidade.getNome(), MatchMode.ANYWHERE));
			}
			
			if(Objects.nonNull(cidade.getEstado())) {
				criteria.add(Restrictions.eq("estado", cidade.getEstado()));
			}
		}
	}

	
}
