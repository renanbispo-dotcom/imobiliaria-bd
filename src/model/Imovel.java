package model;

import java.math.BigDecimal;
import java.util.List;

public class Imovel {

    private Integer codImovel;
    private Double metragem;
    private String status;
    private BigDecimal valorVenda;
    private BigDecimal valorLocacao;
    private Integer qtdQuartos;
    private Integer qtdSuites;
    private Integer qtdGaragens;
    private Proprietario proprietario;
    private Endereco endereco;
    private TipoImovel tipoImovel;
    private List<FotoImovel> fotos;

    public Imovel() {
    }

    public Integer getCodImovel() {
        return codImovel;
    }

    public void setCodImovel(Integer codImovel) {
        this.codImovel = codImovel;
    }

    public Double getMetragem() {
        return metragem;
    }

    public void setMetragem(Double metragem) {
        this.metragem = metragem;
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

    public BigDecimal getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(BigDecimal valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public Integer getQtdQuartos() {
        return qtdQuartos;
    }

    public void setQtdQuartos(Integer qtdQuartos) {
        this.qtdQuartos = qtdQuartos;
    }

    public Integer getQtdSuites() {
        return qtdSuites;
    }

    public void setQtdSuites(Integer qtdSuites) {
        this.qtdSuites = qtdSuites;
    }

    public Integer getQtdGaragens() {
        return qtdGaragens;
    }

    public void setQtdGaragens(Integer qtdGaragens) {
        this.qtdGaragens = qtdGaragens;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TipoImovel getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(TipoImovel tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public List<FotoImovel> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoImovel> fotos) {
        this.fotos = fotos;
    }
}