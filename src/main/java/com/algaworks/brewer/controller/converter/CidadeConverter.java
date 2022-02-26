package com.algaworks.brewer.controller.converter;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.algaworks.brewer.model.Cidade;

public class CidadeConverter implements Converter<String, Cidade> {

	@Override
	public Cidade convert(String codigo) {
		System.out.println("conversor de cidade");
		if(!StringUtils.isEmpty(codigo)) {
			Cidade cidade = new Cidade();
			cidade.setCodigo(Long.valueOf(codigo));
			System.out.println(cidade);
			return cidade;
		}
		
		return null;
	}

}
