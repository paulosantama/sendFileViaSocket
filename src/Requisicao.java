import java.io.Serializable;

enum TipoRequisicao {
    PUT
}

public class Requisicao implements Serializable {

    private TipoRequisicao tipoRequisicao;
    private String action;
    private Object body;

    public TipoRequisicao getTipoRequisicao() {
        return tipoRequisicao;
    }

    public void setTipoRequisicao(TipoRequisicao tipoRequisicao) {
        this.tipoRequisicao = tipoRequisicao;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
