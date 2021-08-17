/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.Mysql;


import com.ksproyectos.parking4all.DAO.Mysql.Entities.Caja;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.core.DAO.CajaQueries;
import com.ksproyectos.parking4all.core.Entities.ICaja;
import com.ksproyectos.parking4all.core.DAO.ICajaDAO;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author kevin
 */
public class CajaDAOImpl extends TransactionalImpl implements ICajaDAO{

    EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

    @Override
    public void create(ICaja entity) {
        try {
            executeTransaction((param)->{
                entity.setIdCaja(null);
                entityManager.persist(entity);
                return true;
            });
        } catch (Parking4AllException ex) {
            Logger.getLogger(CajaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<ICaja> read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ICaja read(ICaja entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void delete(ICaja entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ICaja entity) {
        entityManager.persist(entity);
    }

    @Override
    public ICaja find(CajaQueries by, ICaja entity) {
        switch(by.GETCAJAACTIVA){
            case GETCAJAACTIVA:
                List<ICaja> cajaList =  entityManager.createQuery("SELECT c From Caja c"
                        + " WHERE c.cerrada = 0 AND c.usuariosidUsuarioApertura.idUsuario = :idUsuario")
                        .setParameter("idUsuario", AuthService.getInstance().getIdUsuarioAutenticado())
                .getResultList();
                if(cajaList.isEmpty()){
                    Caja caja = new Caja();
                    caja.setFechaApertura(new Date());
                    caja.setTotal(0);
                    caja.setIdUsuarioApertura(AuthService.getInstance().getIdUsuarioAutenticado());
                    caja.setCerrada(Boolean.FALSE);
                    return caja;
                }
                return cajaList.get(0);
            default:
                throw new UnsupportedOperationException("El query no existe");
        }
    }

    @Override
    public List<ICaja> findAll(CajaQueries by, ICaja entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ICaja getEntity() {
        return new Caja();
    }

    @Override
    public List<ICaja> findAll(CajaQueries by, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
