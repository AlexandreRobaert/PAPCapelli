package br.com.capelli.centrodebelezacapelli.model;

/**
 * Created by alexadre on 06/05/17.
 */

public class Administrador extends Usuario {

    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String senhaWebService;

    public Administrador(){}

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

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getSenhaWebService() {
        return senhaWebService;
    }

    public void setSenhaWebService(String senhaWebService) {
        this.senhaWebService = senhaWebService;
    }
}
