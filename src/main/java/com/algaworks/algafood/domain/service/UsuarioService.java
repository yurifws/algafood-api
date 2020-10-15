package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EmailUsuarioJaCadastradoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaUsuarioNaoCoincideException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService implements IService<Usuario> {

	private static final String MSG_USUARIO_EM_USO = "Usuário de código %d náo pode ser removido, pois está em uso.";

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;  

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
		try {
			usuarioRepository.detach(usuario);
			Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
			
			if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
				throw new EmailUsuarioJaCadastradoException(usuario.getEmail());
			}
			if(usuario.isNovo()) {
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			}
			
			return usuarioRepository.save(usuario);
		}catch (EmailUsuarioJaCadastradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Transactional
	public void atualizarSenha(String senhaAtual, String novaSenha, Usuario usuario) {
		try {
			if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
				throw new SenhaUsuarioNaoCoincideException();
			}
			usuario.setSenha(passwordEncoder.encode(novaSenha));
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

	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = grupoService.buscar(grupoId);
		usuario.associarGrupo(grupo);
	}

	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = grupoService.buscar(grupoId);
		usuario.desassociarGrupo(grupo);
	}

}
