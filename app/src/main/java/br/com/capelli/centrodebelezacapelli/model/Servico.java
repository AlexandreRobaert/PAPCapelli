package br.com.capelli.centrodebelezacapelli.model;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexadre on 05/04/17.
 */

public class Servico {

    private String uid;
    private String nome;
    private Categoria categoria;
    private double valorServico;
    private String tempoEstimado;

    public Servico(){}

    public String getId() {
        return uid;
    }

    public void setId(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Exclude
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public String getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(String tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", nome);
        dados.put("categoria", categoria.getNome());
        dados.put("valorServico", valorServico);
        dados.put("tempoEstimado", tempoEstimado);

        return dados;
    }

    @Exclude
    @Override
    public String toString() {
        return nome + " " + categoria.getNome() + " R$ " + valorServico;
    }
}
