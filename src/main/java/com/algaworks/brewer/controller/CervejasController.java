package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.service.CadastroCervejaService;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {

	private static final Logger logger = LoggerFactory.getLogger(CervejasController.class);

	@Autowired
	private Estilos estilos;

	@Autowired
	private CadastroCervejaService cadastroCervejaService;

	@Autowired
	private Cervejas cervejas;
	
	@GetMapping
	public ModelAndView pesquisa(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size = 2) Pageable pageable,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		
		PageWrapper<Cerveja> pagina = new PageWrapper<>(cervejas.filtrar(cervejaFilter, pageable), request);
		mv.addObject("pagina", pagina);

		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());

		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			System.out.println(">>>> tem erro!!");
			return novo(cerveja);
		}

		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cadastro da cerveja realizado com sucesso!");
		return new ModelAndView("redirect:/cervejas/novo");
	}

}
