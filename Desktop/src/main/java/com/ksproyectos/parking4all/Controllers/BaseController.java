/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.Controllers;

import com.ksproyectos.parking4all.DAO.Mysql.EntityManagerSingleton;
import javax.persistence.EntityManager;

/**
 *
 * @author kevin
 */
class BaseController {
    protected final EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();
}
