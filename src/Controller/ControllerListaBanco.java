package controller;

import Model.ModelListaBanco;
import Dao.DaoListaBanco;
import java.util.ArrayList;

/**
 *
 * @author Luiz Lauro"
 *
 *
 */
public class ControllerListaBanco {

    private DaoListaBanco daoListarBancos = new DaoListaBanco();

    /**
     * recupera ListarBancos
     *
     * @param pBanco
     * @return
     */
    public ArrayList<ModelListaBanco> getListarTabelaBancosController() {
        return this.daoListarBancos.getListarTabelaBancosDAO();
    }

}
