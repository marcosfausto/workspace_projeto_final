package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import br.com.casadocodigo.loja.models.Pedidos;

@Controller
public class PedidosServicoController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@RequestMapping(value="/pedidos", method=RequestMethod.GET)
	public ModelAndView pedidos(RedirectAttributes model) {
		
		 String url ="https://book-payment.herokuapp.com/orders";

         Pedidos[] response = restTemplate.getForObject(url
             ,Pedidos[].class);

         ModelAndView modelAndView = new ModelAndView("/pedidos");

         modelAndView.addObject("response", response);
         return modelAndView;

		
	}

}
