package br.com.capelli.centrodebelezacapelli.model;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexadre on 12/04/17.
 */

public class Promocao {

    private String uid;
    private String descricao;
    private boolean ativo;
    private Calendar dataInicio;
    private Calendar dataFim;
    private int porcentagemDesconto;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }

    public int getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(int porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public Map<String, Object> toMap() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        Map<String, Object> dados = new HashMap<>();
        dados.put("descricao", descricao);
        dados.put("dataInicio", format.format(dataInicio.getTime()));
        dados.put("datafim", format.format(dataFim.getTime()));
        dados.put("porcentualDesconto", porcentagemDesconto);
        dados.put("ativo", ativo);

        return dados;
    }
}
