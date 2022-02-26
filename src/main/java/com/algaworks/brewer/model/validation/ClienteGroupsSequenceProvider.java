package com.algaworks.brewer.model.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.algaworks.brewer.model.Cliente;

public class ClienteGroupsSequenceProvider implements DefaultGroupSequenceProvider<Cliente>{

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {
		List<Class<?>> grupos = new ArrayList<>();
		grupos.add(Cliente.class);
		
		if(isTipoPessoaSelecionada(cliente)) {
			grupos.add(cliente.getTipoPessoa().getGrupo());
		}
		
		return grupos;
	}

	public boolean isTipoPessoaSelecionada(Cliente cliente) {
		return Objects.nonNull(cliente) && Objects.nonNull(cliente.getTipoPessoa());
	}

}
