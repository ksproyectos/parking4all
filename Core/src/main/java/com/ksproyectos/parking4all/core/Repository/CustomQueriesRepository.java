/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Repository;

import com.ksproyectos.parking4all.core.DAO.ICustomQueriesDAO;
import java.util.List;

/**
 *
 * @author kevin
 */
public class CustomQueriesRepository {
    
    ICustomQueriesDAO dao;
    
    public CustomQueriesRepository(ICustomQueriesDAO dao){    
        this.dao = dao;
    }
    
    public List<String> getAllTiposTarifa(){
        return dao.getAllTiposTarifas();
    }
    
}
