package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.Usuario;

@Repository
@Transactional
public class UsuarioDAO implements UserDetailsService{

	@PersistenceContext
	private EntityManager manager;

	public Usuario loadUserByUsername(String email) {
		List<Usuario> usuarios = manager.createQuery("select u from Usuario u where email = :email", Usuario.class)
				.setParameter("email", email)
				.getResultList();
		
		if(usuarios.isEmpty()) {
			throw new UsernameNotFoundException("Usuario " + email + " não foi encontrado");
		}
		
		return usuarios.get(0);
	}
	
	public Usuario getUserById(Integer id) {
		return manager.createQuery("select u from Usuario u where id = :id", Usuario.class)
				.setParameter("id", id)
				.getSingleResult();
		
	}
	
	public boolean checkEmail(String email) {
		    String query = ("select count(email) from Usuario u where email = :email");
			Long count = (Long) manager.createQuery(query).setParameter("email", email).getSingleResult();
			
			if(count > 0) {
				return true;
			}else {
				return false;
			}
			
	}
	
	

	public void gravar(Usuario usuario) {
		manager.persist(usuario);
	}


	public List<Usuario> listar() {
		return manager.createQuery("select u from Usuario u", Usuario.class)
				.getResultList();
	}

	public void atualizaRoles(Usuario usuarioSelecionado) {
		
		manager.createNativeQuery("insert into usuario_role(email,role_nome) values (?,?)")
		.setParameter(1, usuarioSelecionado.getId())
		.setParameter(2, usuarioSelecionado.getRoles())
		.executeUpdate();
		
	}
}