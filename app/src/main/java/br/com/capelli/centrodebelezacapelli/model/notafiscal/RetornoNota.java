package br.com.capelli.centrodebelezacapelli.model.notafiscal;

import java.util.Calendar;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("retorno")
public class RetornoNota {

	@XStreamAlias("codigo")
	private String codigo;
	
	@XStreamAlias("alerta")
	private String alerta;
	
	@XStreamAlias("numero_nfse")
	private int numeroNota;
	
	@XStreamAlias("serie_nfse")
	private byte serieNota;
	
	@XStreamAlias("data_nfse")
	private Calendar dataHoraNota;
	
	@XStreamAlias("arquivo_gerador_nfse")
	private String nomeArquivoXML;
	
	@XStreamAlias("nome_arquivo_gerado_eletron")
	private String nomeArquivoNoServidor;
	
	@XStreamAlias("link_nfse")
	private String linkNota;
	
	@XStreamAlias("cod_verificador_autenticidade")
	private String codValidacaoNota;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(int numeroNota) {
		this.numeroNota = numeroNota;
	}

	public byte getSerieNota() {
		return serieNota;
	}

	public void setSerieNota(byte serieNota) {
		this.serieNota = serieNota;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public Calendar getDataHoraNota() {
		return dataHoraNota;
	}

	public void setDataHoraNota(Calendar dataHoraNota) {
		this.dataHoraNota = dataHoraNota;
	}

	public String getNomeArquivoXML() {
		return nomeArquivoXML;
	}

	public void setNomeArquivoXML(String nomeArquivoXML) {
		this.nomeArquivoXML = nomeArquivoXML;
	}

	public String getNomeArquivoNoServidor() {
		return nomeArquivoNoServidor;
	}

	public void setNomeArquivoNoServidor(String nomeArquivoNoServidor) {
		this.nomeArquivoNoServidor = nomeArquivoNoServidor;
	}

	public String getLinkNota() {
		return linkNota;
	}

	public void setLinkNota(String linkNota) {
		this.linkNota = linkNota;
	}

	public String getCodValidacaoNota() {
		return codValidacaoNota;
	}

	public void setCodValidacaoNota(String codValidacaoNota) {
		this.codValidacaoNota = codValidacaoNota;
	}
}
