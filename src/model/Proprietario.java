package model;

public class Proprietario {

    private Integer codProprietario;
    private String nome;
    private String contato;

    public Proprietario() {
    }

    public Integer getCodProprietario() {
        return codProprietario;
    }

    public void setCodProprietario(Integer codProprietario) {
        this.codProprietario = codProprietario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}