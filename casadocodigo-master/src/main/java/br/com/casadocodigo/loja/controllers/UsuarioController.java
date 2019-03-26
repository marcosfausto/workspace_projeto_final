package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.models.Usuario;
import br.com.casadocodigo.loja.validation.UsuarioValidation;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController  {
	
	@Autowired
	private UsuarioDAO dao;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation());
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Usuario> usuarios = dao.listar();
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
        
        boolean checkEmail = dao.checkEmail(usuario.getEmail());
                
        if(checkEmail == true) {
        	
        	Errors.rejectValue("email", "verify.email");
        	
        	return form(usuario);
			
		}
        
		dao.gravar(usuario);
		
		redirectAttributes.addFlashAttribute("sucesso", "Usuario cadastrado com sucesso!");
		
		return new ModelAndView("redirect:/usuarios");
	}
	
	

}
