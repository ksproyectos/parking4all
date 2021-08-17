/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.SQL;

import com.ksproyectos.parking4all.core.DAO.ICustomQueriesDAO;

import java.util.List;

/**
 *
 * @author kevin
 */
public class CustomQueriesDAOImpl implements ICustomQueriesDAO{

    Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase();

    @Override
    public List<String> getAllTiposTarifas() {
        return db.customQueriesRoomDAO().getAllTiposTarifa();
    }

}
