package br.com.capelli.centrodebelezacapelli.model;

/**
 * Created by alexadre on 27/04/17.
 */

public class Funcionario extends Usuario {

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String senhaWebService;

    public Funcionario(){

    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getSenhaWebService() {
        return senhaWebService;
    }

    public void setSenhaWebService(String senhaWebService) {
        this.senhaWebService = senhaWebService;
    }
}
