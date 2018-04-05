package Dao;

import Model.ModelColunasTabelas;
import conexoes.ConexaoMySql;
import java.util.ArrayList;

/**
 * @author Luiz Lauro"
 */
public class DaoColunasTabelas extends ConexaoMySql {

    /**
     * recupera colunas da tabala
     *
     * @param pTabela
     * @return
     *
     */
    public ArrayList<ModelColunasTabelas> getListarColunasTabela(String pTabela) {
        ArrayList<ModelColunasTabelas> listamodelcColunasTabelas = new ArrayList();
        ModelColunasTabelas modelColunasTabelas = new ModelColunasTabelas();
        try {
            this.conectar();
            this.executarSQL(
                    "SELECT "
                    + "COLUMN_NAME,"
                    + "DATA_TYPE,"
                    + "IS_NULLABLE,"
                    + "COLUMN_KEY"
                    + " FROM"
                    + " COLUMNS"
                    + " WHERE"
                    + " TABLE_NAME = '" + pTabela + "'"
                    + ";"
            );

            while (this.getResultSet().next()) {
                modelColunasTabelas = new ModelColunasTabelas();
                //modelColunasTabelas.setNomeBanco(this.getResultSet().getString(1));
                modelColunasTabelas.setColumn_Name(this.getResultSet().getString(1));
                modelColunasTabelas.setTipo(this.getResultSet().getString(2));
                modelColunasTabelas.setNulo(this.getResultSet().getString(3));
                modelColunasTabelas.setChavePrimary(this.getResultSet().getString(4));
                listamodelcColunasTabelas.add(modelColunasTabelas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.fecharConexao();
        }
        return listamodelcColunasTabelas;
    }

}
