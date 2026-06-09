package model;

import java.math.BigDecimal;

public class AtendimentoImovel {

    private Integer codAtendimento;
    private Imovel imovel;
    private Cliente cliente;
    private Corretor corretor;
    private String dataAtendimento;
    private String status;
    private BigDecimal valorVenda;
    private String observacoes;

    public AtendimentoImovel() {
    }

    public Integer getCodAtendimento() {
        return codAtendimento;
    }

    public void setCodAtendimento(Integer codAtendimento) {
        this.codAtendimento = codAtendimento;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Corretor getCorretor() {
        return corretor;
    }

    public void setCorretor(Corretor corretor) {
        this.corretor = corretor;
    }

    public String getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(String dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
