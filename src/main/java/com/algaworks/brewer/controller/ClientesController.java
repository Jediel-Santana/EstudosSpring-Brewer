package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.algaworks.brewer.model.Cliente;

@Controller
public class ClientesController {

	@RequestMapping("/clientes/novo")
	public String novo() {
		System.out.println("chamando pagina cliente");
		return "cliente/CadastroCliente";
	}

	@RequestMapping(value = "/clientes/novo", method = RequestMethod.POST)
	public String cadastro(Cliente cliente) {
		System.out.println(cliente.getNome());
		return "redirect:/clientes/novo";
	}

}
