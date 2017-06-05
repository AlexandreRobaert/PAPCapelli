package br.com.capelli.centrodebelezacapelli.model;

import java.text.DateFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexadre on 18/05/17.
 */

public class Agendamento {

    private String uid;
    private Servico servico;
    private Cliente cliente;
    private Calendar horario;
    private Funcionario funcionario;
    private boolean bloquearHorario;
    private boolean confirmado;
    private boolean finalizado;
    private double valorTotal;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Calendar getHorario() {
        return horario;
    }

    public void setHorario(Calendar horario) {
        this.horario = horario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public boolean isBloquearHorario() {
        return bloquearHorario;
    }

    public void setBloquearHorario(boolean bloquearHorario) {
        this.bloquearHorario = bloquearHorario;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("servico", servico.getNome());
        map.put("cliente", cliente.getNome());
        map.put("funcionario", funcionario.getNome());
        DateFormat format = DateFormat.getDateTimeInstance();
        map.put("horario", format.format(horario.getTime()));
        map.put("bloquearHorario", bloquearHorario);
        map.put("confirmado", confirmado);
        map.put("finalizado", finalizado);
        map.put("valor", valorTotal);

        return map;
    }
}
