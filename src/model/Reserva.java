package model;

import java.sql.Timestamp;

public class Reserva {
    private int id;
    private long horario;
    private int servicoId;
    private int funcionarioId;
    private int clienteId;

    public Reserva(Timestamp horario,
                   int servicoId,
                   int funcionarioId,
                   int clienteId) {
        this.horario = horario.getTime();
        this.servicoId = servicoId;
        this.funcionarioId = funcionarioId;
        this.clienteId = clienteId;
    }
    
    public Reserva(String horario,
                   int servicoId,
                   int funcionarioId,
                   int clienteId) {
        this.horario = Long.parseLong(horario);
        this.servicoId = servicoId;
        this.funcionarioId = funcionarioId;
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getHorario() {
        return new Timestamp(horario);
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario.getTime();
    }

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
}