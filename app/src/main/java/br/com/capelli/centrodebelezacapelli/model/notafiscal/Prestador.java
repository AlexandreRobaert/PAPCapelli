package br.com.capelli.centrodebelezacapelli.model.notafiscal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("prestador")
public class Prestador {

	@XStreamAlias("cpfcnpj")
    public String cpfCnpj;
    public String cidade = "7513";
	
    public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public String getCidade() {
		return cidade;
	}    
}
