package br.com.capelli.centrodebelezacapelli.model;

/**
 * Created by alexadre on 31/03/17.
 */

public class Categoria {

    private String uid;
    private String nome;

    public Categoria(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
