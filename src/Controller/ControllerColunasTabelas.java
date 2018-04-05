package Controller;

import Dao.DaoColunasTabelas;
import Model.ModelColunasTabelas;
import Model.ModelTabelas;
import java.util.ArrayList;

/**
 *
 * @author Luiz Lauro"
 *
 *
 */
public class ControllerColunasTabelas {

    private DaoColunasTabelas daoColunasTabela = new DaoColunasTabelas();

    /**
     * recupera ListarBancos
     *
     * @param pTabela
     * @return
     */
    public ArrayList<ModelColunasTabelas> getListarColunaTabelaController(String pTabela) {
        return this.daoColunasTabela.getListarColunasTabela(pTabela);
    }

}
