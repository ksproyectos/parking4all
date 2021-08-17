/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.SQL;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Tarifas;
import com.ksproyectos.parking4all.core.DAO.ITarifasDAO;
import com.ksproyectos.parking4all.core.DAO.TarifasQueries;
import com.ksproyectos.parking4all.core.Entities.ITarifas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author kevin
 */
public class TarifasDAOImpl extends BaseDAO implements ITarifasDAO  {

    Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase();

    @Override
    public void create(ITarifas entity) {
        db.tarifasRoomDAO().add((Tarifas) entity);
    }

    @Override
    public List read() {
        return db.tarifasRoomDAO().getAllTarifas();
    }

    @Override
    public ITarifas read(ITarifas iTarifas) {
        return null;
    }

    @Override
    public void delete(ITarifas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ITarifas entity) {
        db.tarifasRoomDAO().update( ( Tarifas) entity);
    }

    @Override
    public ITarifas find(TarifasQueries by, ITarifas entity){
        switch(by){
            case GET_TARIFA_MES_BY_TIPOTARIFA:
                return (ITarifas) db.tarifasRoomDAO().getTarifaMesByTipoTarifa(entity.getTipoTarifa());
            case GET_TARIFA_DIA_BY_TIPOTARIFA:
            case GET_TARIFA_HORA_BY_TIPOTARIFA:
            case GET_TARIFA_MINUTO_BY_TIPOTARIFA:
            default:
                throw new UnsupportedOperationException("El query no existe");
        }
    }

    @Override
    public List<ITarifas> findAll(TarifasQueries by, ITarifas entity) {
        switch(by) {
            case GETALL_TARIFAS_DIA_BY_TIPOTARIFA:
                return toInterface(db.tarifasRoomDAO().getAllTarifasDiaByTipoTarifa(entity.getTipoTarifa()));
            case GETALL_TARIFAS_HORA_BY_TIPOTARIFA:
                return toInterface(db.tarifasRoomDAO().getAllTarifasHoraByTipoTarifa(entity.getTipoTarifa()));
            case GETALL_TARIFAS_MINUTO_BY_TIPOTARIFA:
                return toInterface(db.tarifasRoomDAO().getAllTarifasMinutoByTipoTarifa(entity.getTipoTarifa()));
            default:
                throw new UnsupportedOperationException("El query no existe");
        }
    }



    @Override
    public List<ITarifas> findAll(TarifasQueries by, Object... args) {
        return null;
    }

    @Override
    public ITarifas getEntity() {
        return new Tarifas();
    }

}
