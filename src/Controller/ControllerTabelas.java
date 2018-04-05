package Controller;

import Dao.DaoTabelas;
import Model.ModelTabelas;
import java.util.ArrayList;

/**
 *
 * @author Luiz Lauro"
 *
 *
 */
public class ControllerTabelas {

    private DaoTabelas daoTabelas = new DaoTabelas();

    /**
     * recupera ListarBancos
     *
     * @param pTabela
     * @return
     */
    public ArrayList<ModelTabelas> getListarTabelaBancosController(String pTabela) {
        return this.daoTabelas.getListarTabelaBancosDAO(pTabela);
    }

}
