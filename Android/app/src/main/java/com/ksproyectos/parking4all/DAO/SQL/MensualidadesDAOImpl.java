/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.SQL;

import com.ksproyectos.parking4all.DAO.SQL.Entities.DateConverter;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Mensualidades;
import com.ksproyectos.parking4all.core.DAO.IMensualidadesDAO;
import com.ksproyectos.parking4all.core.DAO.MensualidadesQueries;
import com.ksproyectos.parking4all.core.Entities.IMensualidades;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kevin
 */
public class MensualidadesDAOImpl implements IMensualidadesDAO{

    Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase();

    @Override
    public void create(IMensualidades entity) {
        db.mensualidadesRoomDAO().add((Mensualidades) entity);
    }

    @Override
    public List read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IMensualidades read(IMensualidades iMensualidades) {
        return db.mensualidadesRoomDAO().getMensualidadById(iMensualidades.getIdMensualidad());
    }

    @Override
    public void delete(IMensualidades entity) {
        db.mensualidadesRoomDAO().delete((Mensualidades) entity);
    }

    @Override
    public void update(IMensualidades entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IMensualidades find(MensualidadesQueries by, IMensualidades entity) {
        switch(by){
            case GET_MENSUALIDAD_BY_PLACA:

                Calendar fechaLimite = Calendar.getInstance();
                fechaLimite.add(Calendar.DATE, -30);

                Mensualidades mensualidad = db.mensualidadesRoomDAO().getMensualidadByPlaca(entity.getPlaca(), fechaLimite.getTime(), (new Date()));

                if(mensualidad != null){
                    return mensualidad;
                }else{
                    mensualidad = new Mensualidades();
                    mensualidad.setPlaca("");
                    return mensualidad;
                }
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public List<IMensualidades> findAll(MensualidadesQueries by, IMensualidades entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAll(MensualidadesQueries by, Object... filters){
        switch(by){
            case GET_MENSUALIDAD_BY_FECHAPAGODESDE_FECHAPAGOHASTA:

                return db.mensualidadesRoomDAO().getAllMensualidadesByFechaPagoDesdeHasta(DateConverter.fromDate((Date) filters[0]), DateConverter.fromDate((Date) filters[1]));

            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public IMensualidades getEntity() {
        return new Mensualidades();
    }
}