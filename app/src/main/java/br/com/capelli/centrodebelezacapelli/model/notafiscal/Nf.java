package br.com.capelli.centrodebelezacapelli.model.notafiscal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("nf")
public class Nf {

	@XStreamAlias("valor_total")
	private double valorTotal;
	
	@XStreamAlias("valor_desconto")
	private double valorDesconto;
	
	@XStreamAlias("valor_ir")
	private double valorir;
	
	@XStreamAlias("valor_inss")
	private double valorinss;
	
	@XStreamAlias("valor_contribuicao_social")
	private double valorContribuicaoSocial;
	
	@XStreamAlias("valor_rps")
	private double valorrps;
	
	@XStreamAlias("valor_pis")
	private double valorpis;
	
	@XStreamAlias("valor_cofins")
	private double valorcofins;
	
	@XStreamAlias("observacao")
	private String observacoes;
	
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public double getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public double getValorir() {
		return valorir;
	}
	public void setValorir(double valorir) {
		this.valorir = valorir;
	}
	public double getValorinss() {
		return valorinss;
	}
	public void setValorinss(double valorinss) {
		this.valorinss = valorinss;
	}
	public double getValorContribuicaoSocial() {
		return valorContribuicaoSocial;
	}
	public void setValorContribuicaoSocial(double valorContribuicaoSocial) {
		this.valorContribuicaoSocial = valorContribuicaoSocial;
	}
	public double getValorrps() {
		return valorrps;
	}
	public void setValorrps(double valorrps) {
		this.valorrps = valorrps;
	}
	public double getValorpis() {
		return valorpis;
	}
	public void setValorpis(double valorpis) {
		this.valorpis = valorpis;
	}
	public double getValorcofins() {
		return valorcofins;
	}
	public void setValorcofins(double valorcofins) {
		this.valorcofins = valorcofins;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
		
}
