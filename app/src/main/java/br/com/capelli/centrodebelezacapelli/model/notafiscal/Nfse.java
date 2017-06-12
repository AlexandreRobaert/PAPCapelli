package br.com.capelli.centrodebelezacapelli.model.notafiscal;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

	//	CNPJ 15425039000128
    //	SENHA 154250
    //	CODIGO CIDADE 7513
    //	CODIGO DO SERVIÇO 602

// http://sync.nfs-e.net/datacenter/include/nfw/importa_nfw/nfw_import_upload.php?eletron=1

@XStreamAlias("nfse")
public class Nfse {

    private Nf nf;
    private Prestador prestador;
    private Tomador tomador;
    private List<Item> itens;
    private RetornoNota retornoNota;
    
	public Nf getNf() {
		return nf;
	}
	public void setNf(Nf nf) {
		this.nf = nf;
	}
	public Prestador getPrestador() {
		return prestador;
	}
	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}
	public Tomador getTomador() {
		return tomador;
	}
	public void setTomador(Tomador tomador) {
		this.tomador = tomador;
	}
	public List<Item> getItens() {
		return itens;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	public RetornoNota getRetornoNota() {
		return retornoNota;
	}
	public void setRetornoNota(RetornoNota retornoNota) {
		this.retornoNota = retornoNota;
	}
}
