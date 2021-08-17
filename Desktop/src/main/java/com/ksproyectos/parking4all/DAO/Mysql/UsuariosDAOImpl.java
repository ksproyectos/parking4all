/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.Mysql;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Usuarios;
import com.ksproyectos.parking4all.core.DAO.IUsuariosDAO;
import com.ksproyectos.parking4all.core.Entities.IUsuarios;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author kevin
 */
public class UsuariosDAOImpl extends TransactionalImpl implements IUsuariosDAO{

    EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

    @Override
    public IUsuarios getEntity() {
        return new Usuarios();
    }

    @Override
    public void create(IUsuarios entity) {
        try {
            executeTransaction((param)->{
                entityManager.persist(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<IUsuarios> read() {
        return entityManager.createQuery("SELECT u FROM Usuarios u")
                .getResultList();
    }

    @Override
    public IUsuarios read(IUsuarios entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(IUsuarios entity) {
        try {
            executeTransaction((param)->{
                entityManager.remove(entity);
                return true;    
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(IUsuarios entity) {
        try {
            executeTransaction((param)->{
                entityManager.persist(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
