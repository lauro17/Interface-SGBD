package Dao;

import Model.ModelListaBanco;
import conexoes.ConexaoMySql;
import java.util.ArrayList;

/**
 *
 * @author Luiz Lauro"
 *
 *
 */
public class DaoListaBanco extends ConexaoMySql {

    /**
     * recupera ListarBancos
     *
     * @param pBanco
     * @return
     */
//    public ModelListaBanco getListarTabelaBancosDAO(String pBanco) {
    public ArrayList<ModelListaBanco> getListarTabelaBancosDAO() {
        ArrayList<ModelListaBanco> listamodelListarBancos = new ArrayList();
        ModelListaBanco modelListarTabelasBancos = new ModelListaBanco();
        try {
            this.conectar();
            this.executarSQL(
                    "SELECT "
                    + "SCHEMA_NAME,"
                    + "DEFAULT_CHARACTER_SET_NAME,"
                    + "DEFAULT_COLLATION_NAME"
                    + " FROM"
                    + " SCHEMATA"
                    + ";"
            );

            while (this.getResultSet().next()) {
                modelListarTabelasBancos = new ModelListaBanco();
                modelListarTabelasBancos.setNome_Banco(this.getResultSet().getString(1));
                modelListarTabelasBancos.setCodificacao_caracteres(this.getResultSet().getString(2));
                modelListarTabelasBancos.setNome_Colecao(this.getResultSet().getString(3));
                listamodelListarBancos.add(modelListarTabelasBancos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.fecharConexao();
        }
        return listamodelListarBancos;
    }

}
