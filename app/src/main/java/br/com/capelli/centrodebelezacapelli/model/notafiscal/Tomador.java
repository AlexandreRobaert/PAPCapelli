package br.com.capelli.centrodebelezacapelli.model.notafiscal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("tomador")
public class Tomador {

    public char tipo;
    
    @XStreamAlias("cpfcnpj")
    public String cpfCnpj = "";
    
    @XStreamAlias("nome_razao_social")
    public String nomeRazaoSocial = "";
    
    @XStreamAlias("endereco_informado")
    public String endereco = "";
    
    @XStreamAlias("ie")
    public String ie = "";
    
    @XStreamAlias("sobrenome_nome_fantasia")
    public String sobreNomeFantasia = "";
    
    @XStreamAlias("logradouro")
    public String logradouro = "";
    
    @XStreamAlias("email")
    public String email = "";
    
    @XStreamAlias("numero_residencia")
    public String numeroResidencial = "";

    @XStreamAlias("complemento")
    public String complemento = "";
    
    @XStreamAlias("ponto_referencia")
    public String pontoReferencia = "";

    @XStreamAlias("bairro")
    public String bairro = "";

    @XStreamAlias("cidade")
    public String cidade = "";
    
    @XStreamAlias("cep")
    public String cep = "";
    
    @XStreamAlias("ddd_fone_comercial")
    public String dddFoneComercial = "";
    
    @XStreamAlias("fone_comercial")
    public String foneComercial = "";
    
    @XStreamAlias("ddd_fone_residencial")
    public String dddFoneResidencial = "";
    
    @XStreamAlias("ddd_fax")
    public String dddFax = "";    
    
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public String getNomeRazaoSocial() {
		return nomeRazaoSocial;
	}
	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getIe() {
		return ie;
	}
	public void setIe(String ie) {
		this.ie = ie;
	}
	public String getSobreNomeFantasia() {
		return sobreNomeFantasia;
	}
	public void setSobreNomeFantasia(String sobreNomeFantasia) {
		this.sobreNomeFantasia = sobreNomeFantasia;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumeroResidencial() {
		return numeroResidencial;
	}
	public void setNumeroResidencial(String numeroResidencial) {
		this.numeroResidencial = numeroResidencial;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getPontoReferencia() {
		return pontoReferencia;
	}
	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getDddFoneComercial() {
		return dddFoneComercial;
	}
	public void setDddFoneComercial(String dddFoneComercial) {
		this.dddFoneComercial = dddFoneComercial;
	}
	public String getFoneComercial() {
		return foneComercial;
	}
	public void setFoneComercial(String foneComercial) {
		this.foneComercial = foneComercial;
	}
	public String getDddFoneResidencial() {
		return dddFoneResidencial;
	}
	public void setDddFoneResidencial(String dddFoneResidencial) {
		this.dddFoneResidencial = dddFoneResidencial;
	}
	public String getDddFax() {
		return dddFax;
	}
	public void setDddFax(String dddFax) {
		this.dddFax = dddFax;
	}
	
	    
}
