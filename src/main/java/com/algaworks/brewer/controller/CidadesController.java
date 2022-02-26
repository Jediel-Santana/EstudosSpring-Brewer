package com.algaworks.brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	
	@RequestMapping("/novo")
	public String novo(Cidade cidade) {
		return "cidade/CadastroCidade";
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String cadastro(Cidade cidade) {

		System.out.println(cidade.getNome());

		return "redirect:/cidades/novo";
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(@RequestParam(name = "estado") Long codigoEstado) {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
}
