package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.service.CadastroUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		return new ModelAndView("usuario/CadastroUsuario");
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attribute) {
		if (result.hasErrors()) {
			return novo(usuario);
		}

		ModelAndView mv = new ModelAndView("redirect:/usuarios/novo");
		cadastroUsuarioService.salvar(usuario);
		attribute.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return mv;
	}

}
