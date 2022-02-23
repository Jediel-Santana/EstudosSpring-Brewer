package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.algaworks.brewer.model.Cidade;

@Controller
public class CidadesController {

	@RequestMapping("/cidades/novo")
	public String novo(Cidade cidade) {
		return "cidade/CadastroCidade";
	}

	@RequestMapping(value = "/cidades/novo", method = RequestMethod.POST)
	public String cadastro(Cidade cidade) {

		System.out.println(cidade.getNome());

		return "redirect:/cidades/novo";
	}

}
