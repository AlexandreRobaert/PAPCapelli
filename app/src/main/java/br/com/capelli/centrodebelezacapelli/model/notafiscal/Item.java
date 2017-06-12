package br.com.capelli.centrodebelezacapelli.model.notafiscal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("lista")
public class Item {

	@XStreamAlias("codigo_local_prestacao_servico")
    private String codigoCidade = "7513";
	
	@XStreamAlias("codigo_item_lista_servico")
    private String codigoServico = "602";
	
	@XStreamAlias("descritivo")
    private String descricaoServico;
	
	@XStreamAlias("aliquota_item_lista_servico")
    private double aliquotaItemServico = 3.0;
	
	@XStreamAlias("situacao_tributaria")
    private int codigoSituacaoTributaria = 0;
	
	@XStreamAlias("unidade_codigo")
	private byte unidadeCodigo = 1;
	
	@XStreamAlias("unidade_quantidade")
	private int quantidadeItem;
	
	@XStreamAlias("unidade_valor_unitario")
    private double valorItem;
	
	@XStreamAlias("tributa_municipio_prestador")
	private boolean tributaMunicipioPrestador = true;
	
	@XStreamAlias("valor_deducao")
	private double valorDeducao;
	
	@XStreamAlias("valor_tributavel")
	private double valorTributavel = valorItem * quantidadeItem;
	
	@XStreamAlias("valor_issrf")
	private double valorISSRF;	
    
	public String getCodigoCidade() {
		return codigoCidade;
	}
	public String getCodigoServico() {
		return codigoServico;
	}
	public String getDescricaoServico() {
		return descricaoServico;
	}
	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}
	public double getAliquotaItemServico() {
		return aliquotaItemServico;
	}
	public void setAliquotaItemServico(double aliquotaItemServico) {
		this.aliquotaItemServico = aliquotaItemServico;
	}
	public int getCodigoSituacaoTributaria() {
		return codigoSituacaoTributaria;
	}	
	public byte getUnidadeCodigo() {
		return unidadeCodigo;
	}
	public int getQuantidadeItem() {
		return quantidadeItem;
	}
	public void setQuantidadeItem(int quantidadeItem) {
		this.quantidadeItem = quantidadeItem;
		this.valorTributavel = quantidadeItem * valorItem;
	}
	public double getValorItem() {
		return valorItem;
	}
	public void setValorItem(double valorItem) {
		this.valorItem = valorItem;
		this.valorTributavel = valorItem * quantidadeItem;
	}
	public boolean isTributaMunicipioPrestador() {
		return tributaMunicipioPrestador;
	}
	public void setTributaMunicipioPrestador(boolean tributaMunicipioPrestador) {
		this.tributaMunicipioPrestador = tributaMunicipioPrestador;
	}
	public double getValorDeducao() {
		return valorDeducao;
	}
	public void setValorDeducao(double valorDeducao) {
		this.valorDeducao = valorDeducao;
	}
	public double getValorTributavel() {
		return valorTributavel;
	}
	public void setValorTributavel(double valorTributavel) {
		this.valorTributavel = valorTributavel;
	}
	public double getValorISSRF() {
		return valorISSRF;
	}
	public void setValorISSRF(double valorISSRF) {
		this.valorISSRF = valorISSRF;
	}
	public void setCodigoCidade(String codigoCidade) {
		this.codigoCidade = codigoCidade;
	}
	public void setCodigoServico(String codigoServico) {
		this.codigoServico = codigoServico;
	}
	public void setCodigoSituacaoTributaria(int codigoSituacaoTributaria) {
		this.codigoSituacaoTributaria = codigoSituacaoTributaria;
	}
	public void setUnidadeCodigo(byte unidadeCodigo) {
		this.unidadeCodigo = unidadeCodigo;
	}
	
	
}
