package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.service.exception.EmailUsuarioJaCadastradoException;
import com.algaworks.brewer.service.exception.SenhaUsuarioObrigatoriaException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;

	@Autowired
	private PasswordEncoder encoder;

	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioBuscado = usuarios.findByEmail(usuario.getEmail());

		if (usuarioBuscado.isPresent())
			throw new EmailUsuarioJaCadastradoException();

		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha()))
			throw new SenhaUsuarioObrigatoriaException("A senha é obrigatória");

		if (usuario.isNovo()) {
			usuario.setSenha(encoder.encode(usuario.getSenha()));
			usuario.setSenhaConfirmacao(usuario.getSenha());
		}

		usuarios.save(usuario);
	}

	@Transactional
	public void atualizarStatus(Long[] codigos, StatusUsuario status) {
		status.executar(codigos, usuarios);
	}

}
