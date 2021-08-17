/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.SQL;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.core.DAO.IMovimientosDAO;
import com.ksproyectos.parking4all.core.DAO.MovimientosQueries;
import com.ksproyectos.parking4all.core.Entities.IMovimientos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author kevin
 */
public class MovimientosDAOImpl extends TransactionalImpl implements IMovimientosDAO{

    Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase();

    private final String FECHA_LIMITE_USO = "30/01/2020";

    public MovimientosDAOImpl(){

    }

    @Override
    public void create(IMovimientos entity) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date limit = null;
        try {
            limit = df.parse(FECHA_LIMITE_USO);
        } catch (ParseException e) {
            throw new RuntimeException("Error al leer fecha expiracion del sistema");
        }
        if((new Date()).getTime() < limit.getTime()) {
            Long id = db.movimientosRoomDAO().add((Movimientos) entity);
            entity.setIdMovimiento(id.intValue());
        }
    }


    @Override
    public List<IMovimientos> read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IMovimientos read(IMovimientos iMovimientos) {
        return db.movimientosRoomDAO().getById(iMovimientos.getIdMovimiento());
    }

    @Override
    public void delete(IMovimientos entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(IMovimientos entity) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date limit = null;
        try {
            limit = df.parse(FECHA_LIMITE_USO);
        } catch (ParseException e) {
            throw new RuntimeException("Error al leer fecha expiracion del sistema");
        }
        if((new Date()).getTime() < limit.getTime()){
            Movimientos movimiento = (Movimientos) entity;
            db.movimientosRoomDAO().update(movimiento);
        }
    }

    @Override
    public IMovimientos find(MovimientosQueries by, IMovimientos entity) {
        return null;
    }

    @Override
    public List<IMovimientos> findAll(MovimientosQueries by, IMovimientos entity) {
        switch(by){
            case GET_BY_PLACA:
                return BaseDAO.toInterface(db.movimientosRoomDAO().getAllMovimientosByPlaca(entity.getPlaca()));

            default:
                throw new UnsupportedOperationException("El query " + by.toString() + " no esta implementado en " + this.getClass().getName()); //To change body of generated methods, choose Tools | Templates.

        }

    }

    @Override
    public List<IMovimientos> findAll(MovimientosQueries by, Object... filters) {

        switch(by){
            case GET_ALL_MOVIMIENTOS_ENTRADA_BY_FECHA_DESDE_HASTA:
                if(null != filters[2] && !((List) filters[2]).isEmpty()){
                    return  BaseDAO.toInterface(
                            db.movimientosRoomDAO()
                                    .getAllMovimientosEntradaByFechaDesdeHasta(
                                            (Date) filters[0], (Date) filters[1], (List) filters[2]));
                }else{
                    return  BaseDAO.toInterface(
                            db.movimientosRoomDAO()
                                    .getAllMovimientosEntradaByFechaDesdeHasta(
                                            (Date) filters[0], (Date) filters[1]));
                }



            case GET_ALL_MOVIMIENTOS_SALIDA_BY_FECHA_DESDE_HASTA:
                if(null != filters[2] && !((List) filters[2]).isEmpty()){
                    return  BaseDAO.toInterface(
                            db.movimientosRoomDAO()
                                    .getAllMovimientosSalidaByFechaDesdeHasta(
                                            (Date) filters[0], (Date) filters[1], (List) filters[2]));
                }else{
                    return  BaseDAO.toInterface(
                            db.movimientosRoomDAO()
                                    .getAllMovimientosSalidaByFechaDesdeHasta(
                                            (Date) filters[0], (Date) filters[1]));
                }

            default:
                throw new UnsupportedOperationException("El query " + by.toString() + " no esta implementado en " + this.getClass().getName()); //To change body of generated methods, choose Tools | Templates.

        }

    }

    @Override
    public IMovimientos getEntity() {
        return new Movimientos();
    }

}
