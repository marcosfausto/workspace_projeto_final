package br.com.casadocodigo.loja.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.Relatorio;

@Controller
@RequestMapping("/relatorio-produtos")
public class RelatorioProdutosController {
	
	@Autowired
	private ProdutoDAO dao;
	
/*	@RequestMapping( method=RequestMethod.GET)
	@ResponseBody
	public List<Produto> detalheJson() {
		
		return dao.listar();
	
	}*/
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Relatorio detalheJson(@RequestParam(value="data", required=false) String dataLancamento) throws ParseException{
		
		Relatorio relatorio = new Relatorio() ;
				
		Date dateAtual = Calendar.getInstance().getTime();
        
		relatorio.setDataGeracao(dateAtual);
							
		
		
		if(dataLancamento!=null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(dataLancamento);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			           
	        relatorio.setProdutos(dao.findPorData(cal));
	        relatorio.setQuantidade(dao.contaProdutos(cal));

	        
	           
		}else {
	          relatorio.setQuantidade(dao.contaProdutos());
			  relatorio.setProdutos(dao.listar());
		}
		
		return relatorio;
		
	}
	
	
}
