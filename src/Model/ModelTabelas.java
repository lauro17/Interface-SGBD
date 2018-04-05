package Model;

/**
 *
 * @author Luiz Lauro
 */
public class ModelTabelas {

    private String Tabela;
    private String TabelaQTDRegistros;

    /**
     * @return the Tabela
     */
    public String getTabela() {
        return Tabela;
    }

    /**
     * @param Tabela the Tabela to set
     */
    public void setTabela(String Tabela) {
        this.Tabela = Tabela;
    }

    /**
     * @return the TabelaQTDRegistros
     */
    public String getTabelaQTDRegistros() {
        return TabelaQTDRegistros;
    }

    /**
     * @param TabelaQTDRegistros the TabelaQTDRegistros to set
     */
    public void setTabelaQTDRegistros(String TabelaQTDRegistros) {
        this.TabelaQTDRegistros = TabelaQTDRegistros;
    }

    @Override
    public String toString() {
        return "ModelListarBancos {"
                + "::Tabela = " + this.getTabela()
                + "::TabelaQTDRegistros = " + this.getTabelaQTDRegistros()
                + "}";

    }
}
