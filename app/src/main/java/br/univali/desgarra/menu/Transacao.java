package br.univali.desgarra.menu;

public class Transacao {
    private int id, id_usuario_vendedor, id_pelucia, id_usuario_recebedor;
    String tipo_transacao;

    public Transacao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario_vendedor() {
        return id_usuario_vendedor;
    }

    public void setId_usuario_vendedor(int id_usuario_vendedor) {
        this.id_usuario_vendedor = id_usuario_vendedor;
    }

    public int getId_pelucia() {
        return id_pelucia;
    }

    public void setId_pelucia(int id_pelucia) {
        this.id_pelucia = id_pelucia;
    }

    public int getId_usuario_recebedor() {
        return id_usuario_recebedor;
    }

    public void setId_usuario_recebedor(int id_usuario_recebedor) {
        this.id_usuario_recebedor = id_usuario_recebedor;
    }

    public String getTipo_transacao() {
        return tipo_transacao;
    }

    public void setTipo_transacao(String tipo_transacao) {
        this.tipo_transacao = tipo_transacao;
    }
}