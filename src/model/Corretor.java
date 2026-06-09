package model;

public class Corretor {

    private Integer codCorretor;

    private String nomeCorretor;

    private String creci;

    private Coordenador coordenador;

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

    public Coordenador getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
    }

    @Override
    public String toString() {
        return "Corretor{" +
                "codCorretor=" + codCorretor +
                ", nomeCorretor='" + nomeCorretor + '\'' +
                ", creci='" + creci + '\'' +
                ", coordenador=" +
                (coordenador != null
                        ? coordenador.getCodCoordenador()
                        : null) +
                '}';
    }
}