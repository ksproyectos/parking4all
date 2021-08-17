/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.Mysql;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Movimientos;
import com.ksproyectos.parking4all.Services.LicenciaService;
import com.ksproyectos.parking4all.core.DAO.IMovimientosDAO;
import com.ksproyectos.parking4all.core.DAO.MovimientosQueries;
import com.ksproyectos.parking4all.core.Entities.IMovimientos;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author kevin
 */
public class MovimientosDAOImpl extends TransactionalImpl implements IMovimientosDAO{
    
    EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();
    
    @Override
    public void create(IMovimientos entity) {
        try {
            executeTransaction((param)->{
                entityManager.persist(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(MovimientosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<IMovimientos> read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(IMovimientos entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(IMovimientos entity) {
        try {
            executeTransaction((param)->{

                entityManager.persist(entity);

                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(MovimientosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public IMovimientos find(MovimientosQueries by, IMovimientos entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IMovimientos> findAll(MovimientosQueries by, IMovimientos entity) {
        
        switch(by){
            case GET_BY_PLACA:
                Query getMovimientoByPlacaQuery = 
                    entityManager
                    .createQuery("SELECT m FROM Movimientos m"
                            + " WHERE m.placa"
                            + " LIKE :placa"
                            + " AND m.finalizado = 0");
                
                entityManager.clear();
                
                getMovimientoByPlacaQuery
                .setParameter("placa", "%" + entity.getPlaca() + "%");
                
                return getMovimientoByPlacaQuery.getResultList();
            
            default:
                throw new UnsupportedOperationException("El query " + by.toString() + " no esta implementado en " + this.getClass().getName()); //To change body of generated methods, choose Tools | Templates.
        
        }
        
    }

    @Override
    public IMovimientos getEntity() {
        return new Movimientos();
    }

    @Override
    public IMovimientos read(IMovimientos entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IMovimientos> findAll(MovimientosQueries by, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
