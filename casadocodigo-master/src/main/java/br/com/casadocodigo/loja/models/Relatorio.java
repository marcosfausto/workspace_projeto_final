package br.com.casadocodigo.loja.models;

import java.util.Date;
import java.util.List;


public class Relatorio {
	
	public Relatorio() {
		
	}
	
	private Date dataGeracao;
	
	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	private Long quantidade;
	
	private List<Produto> produtos;

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}


}
