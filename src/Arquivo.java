import java.io.Serializable;

public class Arquivo implements Serializable {

    public static final long serialVersionUID = 1L;

    private String nome;
    private byte[] conteudo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

}
