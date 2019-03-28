package br.com.casadocodigo.loja.controllers;

import java.util.ArrayList; 
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.RoleDAO;
import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;
import br.com.casadocodigo.loja.validation.UsuarioValidation;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController  {
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
	@Autowired
	private RoleDAO roleDao;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation());
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Usuario> usuarios = usuarioDao.listar();
		ModelAndView modelAndView = new ModelAndView("usuarios/lista");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}
	
	@RequestMapping("/form")
	public ModelAndView form(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuarios/form");
	//	modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}
	
	@RequestMapping("/role/{id}")
	public ModelAndView role(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("usuarios/role");
		Usuario usuario = usuarioDao.getUserById(id);

	        List<Role> rolesDisponiveis = roleDao.listar();

	        List<Role> checkedRoles = usuario.getRoles();        
	        usuario.setRoles(checkedRoles);


	        List<Role> roles = new ArrayList<Role>();
	        for (Role role:rolesDisponiveis){
	            roles.add(role);
	        }

	        modelAndView.addObject("usuario", usuario);
	        modelAndView.addObject("roles", roles);

		return modelAndView;
	}
	
	
	
    @RequestMapping("/lista")
    public ModelAndView atualizarRoles(Usuario usuario, RedirectAttributes redirectAttributes){

        List<Role> rolesSelecionadas = usuario.getRoles();
        Usuario usuarioSelecionado = usuarioDao.getUserById(usuario.getId());
        usuarioSelecionado.setRoles(rolesSelecionadas);

        usuarioDao.atualizaRoles(usuarioSelecionado);
        
        redirectAttributes.addFlashAttribute("sucesso", "Permissões alteradas com sucesso!");
        
		return new ModelAndView("redirect:/usuarios");
    }
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView gravar(@Valid Usuario usuario, BindingResult result, 
				RedirectAttributes redirectAttributes, Errors Errors){
		
		if(result.hasErrors()) {
			return form(usuario);
		}
		
	
	    BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        String encodedPassword  = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encodedPassword);
        usuario.setSenhaConfirma(encodedPassword);
        
        boolean checkEmail = usuarioDao.checkEmail(usuario.getEmail());
                
        if(checkEmail == true) {
        	
        	Errors.rejectValue("email", "verify.email");
        	
        	return form(usuario);
			
		}
        
		usuarioDao.gravar(usuario);
		
		redirectAttributes.addFlashAttribute("sucesso", "Usuario cadastrado com sucesso!");
		
		return new ModelAndView("redirect:/usuarios");
	}
	
	

}
