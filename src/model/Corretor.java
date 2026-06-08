package model;

public class Corretor {

    private Integer codCorretor;
    private String nomeCorretor;
    private String creci;
    private Integer codCoordenador;

    public Corretor() {
    }

    public Integer getCodCorretor() {
        return codCorretor;
    }

    public void setCodCorretor(Integer codCorretor) {
        this.codCorretor = codCorretor;
    }

    public String getNomeCorretor() {
        return nomeCorretor;
    }

    public void setNomeCorretor(String nomeCorretor) {
        this.nomeCorretor = nomeCorretor;
    }

    public String getCreci() {
        return creci;
    }

    public void setCreci(String creci) {
        this.creci = creci;
    }

    public Integer getCodCoordenador() {
        return codCoordenador;
    }

    public void setCodCoordenador(Integer codCoordenador) {
        this.codCoordenador = codCoordenador;
    }
}