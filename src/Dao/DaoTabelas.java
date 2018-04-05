package Dao;

import Model.ModelListaBanco;
import Model.ModelTabelas;
import conexoes.ConexaoMySql;
import java.util.ArrayList;

/**
 * @author Luiz Lauro"
 */
public class DaoTabelas extends ConexaoMySql {

    /**
     * recupera ListarBancos
     *
     * @param pBanco
     * @return
     *
     */
    public ArrayList<ModelTabelas> getListarTabelaBancosDAO(String pTabela) {
        ArrayList<ModelTabelas> listamodelListarTabelaBancos = new ArrayList();
        ModelTabelas modelTabelas = new ModelTabelas();
        try {
            this.conectar();
            this.executarSQL(
                    "SELECT "
                    + "TABLE_SCHEMA,"
                    + "TABLE_NAME,"
                    + "TABLE_ROWS"
                    + " FROM"
                    + " TABLES"
                    + " WHERE"
                    + " TABLE_SCHEMA = '" + pTabela + "'"
                    + ";"
            );

            while (this.getResultSet().next()) {
                modelTabelas = new ModelTabelas();
//                modelTabelas.setNomeBanco(this.getResultSet().getString(1));
                modelTabelas.setTabela(this.getResultSet().getString(2));
                modelTabelas.setTabelaQTDRegistros(this.getResultSet().getString(3));
                listamodelListarTabelaBancos.add(modelTabelas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.fecharConexao();
        }
        return listamodelListarTabelaBancos;
    }

}
