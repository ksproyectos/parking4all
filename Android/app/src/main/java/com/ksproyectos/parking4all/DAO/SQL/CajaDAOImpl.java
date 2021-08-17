/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.SQL;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Caja;
import com.ksproyectos.parking4all.core.DAO.CajaQueries;
import com.ksproyectos.parking4all.core.DAO.ICajaDAO;
import com.ksproyectos.parking4all.core.Entities.ICaja;

import java.util.Date;
import java.util.List;

/**
 *
 * @author kevin
 */
public class CajaDAOImpl implements ICajaDAO{

    Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase();

    @Override
    public void create(ICaja entity) {
        db.cajaRoomDAO().addReplace((Caja) entity);
    }

    @Override
    public List<ICaja> read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ICaja read(ICaja Caja) {
        return db.cajaRoomDAO().getById(Caja.getIdCaja());
    }

    @Override
    public void delete(ICaja entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ICaja entity) {
        db.cajaRoomDAO().addReplace((Caja) entity);
    }

    @Override
    public ICaja find(CajaQueries by, ICaja entity) {
        switch(by) {
            case GETCAJAACTIVA:
                ICaja caja = (ICaja) db.cajaRoomDAO().getCajaActiva();
                if (caja == null) {
                    caja = new Caja();
                    caja.setFechaApertura(new Date());
                    caja.setTotal(0);
                    db.cajaRoomDAO().addReplace((Caja) caja);
                    caja = (ICaja) db.cajaRoomDAO().getCajaActiva();
                }
                return caja;
            default:
                throw new UnsupportedOperationException("El query no existe");
        }

    }

    @Override
    public List<ICaja> findAll(CajaQueries by, ICaja entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ICaja> findAll(CajaQueries by, Object... args) {
        return null;
    }

    @Override
    public ICaja getEntity() {
        return new Caja();
    }

}