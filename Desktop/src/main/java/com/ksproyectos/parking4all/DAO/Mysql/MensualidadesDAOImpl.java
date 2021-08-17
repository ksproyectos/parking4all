/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.Mysql;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Mensualidades;
import com.ksproyectos.parking4all.core.DAO.DAOException;
import com.ksproyectos.parking4all.core.DAO.IMensualidadesDAO;
import com.ksproyectos.parking4all.core.DAO.MensualidadesQueries;
import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author kevin
 */
public class MensualidadesDAOImpl extends TransactionalImpl implements IMensualidadesDAO{

    EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

    @Override
    public void create(IMensualidades entity) {
        try {
            executeTransaction((param)->{
                entityManager.persist(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(MensualidadesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<IMensualidades> read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(IMensualidades entity) {
        try {
            executeTransaction((param)->{
                entityManager.remove(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(MensualidadesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(IMensualidades entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IMensualidades find(MensualidadesQueries by, IMensualidades entity){
        switch(by){
                case GET_MENSUALIDAD_BY_PLACA:
                    Calendar fechaLimite = Calendar.getInstance();
                    fechaLimite.add(Calendar.DATE, -30);
                    List<IMensualidades> mensualidadesList = entityManager.createQuery("SELECT m From Mensualidades m"
                                + " WHERE m.placa = :placa"
                                + " AND m.fechaPago"
                                + " BETWEEN :fechaLimite AND CURRENT_DATE")
                        .setParameter("placa", entity.getPlaca())
                        .setParameter("fechaLimite", fechaLimite.getTime())
                            .getResultList();
                    if(mensualidadesList.isEmpty()){
                        return null;
                    }
                    return mensualidadesList.get(0);
                default:
                    throw new UnsupportedOperationException("Not supported yet.");
            }
    }

    @Override
    public List<IMensualidades> findAll(MensualidadesQueries by, IMensualidades entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IMensualidades getEntity() {
        return new Mensualidades();
    }

    @Override
    public IMensualidades read(IMensualidades entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IMensualidades> findAll(MensualidadesQueries by, Object... filters) {
        
        if(by == MensualidadesQueries.GET_MENSUALIDAD_BY_FECHAPAGODESDE_FECHAPAGOHASTA){
        
            return entityManager.createQuery("SELECT m From Mensualidades m"
                    + " WHERE m.fechaPago BETWEEN :fechaPagoDesde AND :fechaPagoHasta")
                    .setParameter("fechaPagoDesde", (Date) filters[0])
                    .setParameter("fechaPagoHasta", (Date) filters[1])
                    .getResultList();
        
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
