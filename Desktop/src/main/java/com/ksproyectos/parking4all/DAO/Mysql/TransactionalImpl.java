/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.Mysql;

import com.ksproyectos.parking4all.core.DAO.FunctionWithException;
import com.ksproyectos.parking4all.core.DAO.ITransactional;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.function.Function;
import javax.persistence.EntityManager;

/**
 *
 * @author kevin
 */
public class TransactionalImpl implements ITransactional{
    
    EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();
    
    @Override
    public boolean executeTransaction(FunctionWithException<Void, Boolean> transaction) throws Parking4AllException {
        if(transaction.apply(null)){
            if(!entityManager.getTransaction().isActive()){
                entityManager.getTransaction().begin();
                entityManager.flush();
                entityManager.getTransaction().commit();
            }
            
        }else{
            entityManager.clear();
        }
        return true;
    }
    
}
