package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaUsuarioNaoCoincideException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService implements IService<Usuario> {

	private static final String MSG_USUARIO_EM_USO = "Usuário de código %d náo pode ser removida, pois está em uso.";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario buscar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

	@Override
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void atualizarSenha(String senhaAtual, String novaSenha, Usuario usuario) {
		try {
			if (usuario.senhaAtualNaoCoincideSenhaUsuario(senhaAtual)) {
				throw new SenhaUsuarioNaoCoincideException();
			}
			usuario.setSenha(novaSenha);
			usuarioRepository.save(usuario);
		}catch (SenhaUsuarioNaoCoincideException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}

	@Override
	@Transactional
	public void remover(Long id) {
		try {
			usuarioRepository.deleteById(id);
			usuarioRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, id));
		}

	}

}
