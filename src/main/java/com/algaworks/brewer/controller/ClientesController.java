package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.service.CadastroClienteService;
import com.algaworks.brewer.service.CpfOuCpnjClienteJaCadastradoException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private Estados estados;

	@Autowired
	private CadastroClienteService cadastroClienteService;

	@Autowired
	private Clientes clientes;

	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, @PageableDefault(size = 3) Pageable pageable, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("cliente/PesquisaCliente");
		// add filtro
		PageWrapper<Cliente> pagina = new PageWrapper<>(clientes.filtrar(clienteFilter, pageable), req);

		return mv;
	}

	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());

		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attribute) {

		if (result.hasErrors()) {
			return novo(cliente);
		}
		try {
			cadastroClienteService.salvar(cliente);
		} catch (CpfOuCpnjClienteJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}

		attribute.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
		return new ModelAndView("redirect:/clientes/novo");
	}

}
