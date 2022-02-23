package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.service.CadastroEstiloService;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilos")
public class EstilosController {

	@Autowired
	private CadastroEstiloService cadastroEstiloService;

	@Autowired
	private Estilos estilos;

	@RequestMapping("/novo")
	public String novo(Estilo estilo) {
		return "estilo/CadastroEstilo";
	}

	@GetMapping
	public ModelAndView pesquisa(EstiloFilter estiloFilter, @PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("estilo/PesquisaEstilo");

		PageWrapper<Estilo> page = new PageWrapper<>(estilos.filtrar(estiloFilter, pageable), httpServletRequest);
		mv.addObject("pagina", page);

		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String cadastro(@Valid Estilo estilo, BindingResult result, RedirectAttributes attribute) {
		if (result.hasErrors()) {
			return novo(estilo);
		}

		try {
			cadastroEstiloService.salvar(estilo);
		} catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}

		attribute.addFlashAttribute("mensagem", "Estilo cadastrado com sucesso!");
		return "estilo/CadastroEstilo";
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());

		estilo = cadastroEstiloService.salvar(estilo);

		return ResponseEntity.ok(estilo);
	}

}
