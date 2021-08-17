/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.Mysql;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Tarifas;
import com.ksproyectos.parking4all.core.DAO.DAOException;
import com.ksproyectos.parking4all.core.DAO.ITarifasDAO;
import com.ksproyectos.parking4all.core.DAO.TarifasQueries;
import com.ksproyectos.parking4all.core.Entities.ITarifas;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author kevin
 */
public class TarifasDAOImpl extends TransactionalImpl implements ITarifasDAO{

    EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

    @Override
    public void create(ITarifas entity) {
        try {
            executeTransaction((param)->{
                entityManager.persist(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(TarifasDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<ITarifas> read() {
        return entityManager.createQuery("SELECT t From Tarifas t")
                .getResultList();
    }
    
    @Override
    public ITarifas read(ITarifas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(ITarifas entity) {
        try {
            executeTransaction((param)->{
                entityManager.remove(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(TarifasDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ITarifas entity) {
        try {
            executeTransaction((param)->{
                entityManager.persist(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(TarifasDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ITarifas find(TarifasQueries by, ITarifas entity){
        switch(by){
            case GET_TARIFA_DIA_BY_TIPOTARIFA:
                return (ITarifas) entityManager
                        .createQuery("SELECT t From Tarifas t"
                                + " WHERE t.tipoTarifa = :tipoTarifa"
                                + " AND t.activa = TRUE "
                                + " AND t.dias = 1")
                .setParameter("tipoTarifa", entity.getTipoTarifa())
                .getResultList().get(0);
            case GET_TARIFA_HORA_BY_TIPOTARIFA:
                return (ITarifas) entityManager
                        .createQuery("SELECT t From Tarifas t"
                                + " WHERE t.tipoTarifa = :tipoTarifa"
                                + " AND t.activa = TRUE "
                                + " AND t.horas = 1")
                .setParameter("tipoTarifa", entity.getTipoTarifa())
                .getResultList().get(0);
            case GET_TARIFA_MINUTO_BY_TIPOTARIFA:
                return (ITarifas) entityManager
                        .createQuery("SELECT t From Tarifas t"
                                + " WHERE t.tipoTarifa = :tipoTarifa"
                                + " AND t.activa = TRUE "
                                + " AND t.minutos = 1")
                .setParameter("tipoTarifa", entity.getTipoTarifa())
                .getResultList().get(0);
            case GET_TARIFA_MES_BY_TIPOTARIFA:
                return  (Tarifas) entityManager.createQuery(
                        "SELECT t From Tarifas"
                        + " t WHERE t.tipoTarifa = :tipoTarifa "
                        + " AND t.activa = TRUE "
                        + "AND t.dias = 30 ORDER BY t.dias asc")
                .setParameter("tipoTarifa", entity.getTipoTarifa())
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList().get(0);
            default:
                throw new UnsupportedOperationException("El query no existe");
        } 
    }

    @Override
    public List<ITarifas> findAll(TarifasQueries by, ITarifas entity) {
        switch(by){
            case GETALL_TARIFAS_DIA_BY_TIPOTARIFA:
                return entityManager.createQuery("SELECT t From Tarifas t"
                    + " WHERE t.tipoTarifa = :tipoTarifa"
                    + " AND t.dias > 0"
                    + " AND t.activa = TRUE "
                    + " ORDER BY t.dias desc")
                .setParameter("tipoTarifa", entity.getTipoTarifa())
                .getResultList();
            case GETALL_TARIFAS_HORA_BY_TIPOTARIFA:
                return entityManager.createQuery("SELECT t From Tarifas t"
                    + " WHERE t.tipoTarifa = :tipoTarifa"
                    + " AND t.horas > 0"
                    + " AND t.activa = TRUE "
                    + " ORDER BY t.horas desc")
                .setParameter("tipoTarifa", entity.getTipoTarifa())
                .getResultList();
            case GETALL_TARIFAS_MINUTO_BY_TIPOTARIFA:
                return entityManager.createQuery("SELECT t From Tarifas t"
                    + " WHERE t.tipoTarifa = :tipoTarifa"
                    + " AND t.minutos > 0"
                    + " AND t.activa = TRUE "
                    + " ORDER BY t.minutos desc")
                .setParameter("tipoTarifa", entity.getTipoTarifa())
                .getResultList();

            case GETALL_TARIFAS_PREPAGADAS:
                return entityManager.createQuery("SELECT t From Tarifas t"
                        + " WHERE t.prepagada = true "
                        + " AND t.activa = TRUE")
                        .setParameter("tipoTarifa", entity.getTipoTarifa())
                        .getResultList();
            default:
                throw new UnsupportedOperationException("El query no existe");
        }   
    }

    @Override
    public ITarifas getEntity() {
        return new Tarifas();
    }

    @Override
    public List<ITarifas> findAll(TarifasQueries by, Object... args) {
        switch(by){
            case GETALL_TARIFAS_PREPAGADAS:
                return entityManager.createQuery("SELECT t From Tarifas t"
                        + " WHERE t.prepagada = true "
                        + " AND t.activa = TRUE")
                        .getResultList();
            default:
                throw new UnsupportedOperationException("El query no existe");
        }
    }
}
