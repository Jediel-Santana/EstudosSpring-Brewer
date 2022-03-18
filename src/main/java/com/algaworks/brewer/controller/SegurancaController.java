package com.algaworks.brewer.controller;

import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SegurancaController {
	
	@GetMapping("/login")
	public String login(@AuthenticationPrincipal User user) {
		if(Objects.nonNull(user))
			return "redirect:/cervejas";
		return "Login";
	}
	
	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}
	
}
