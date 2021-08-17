/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.Mysql;

import com.ksproyectos.parking4all.core.DAO.ICustomQueriesDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author kevin
 */
public class CustomQueriesDAOImpl implements ICustomQueriesDAO{

    EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

    @Override
    public List<String> getAllTiposTarifas() {
         Query tarifasQuery =
                entityManager
                        .createQuery("SELECT DISTINCT(t.tipoTarifa)"
                                + " FROM Tarifas t WHERE t.activa = 1");
        return tarifasQuery.getResultList();
    }
    
}
