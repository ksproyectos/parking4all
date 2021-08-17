/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Repository;

import com.ksproyectos.parking4all.core.DAO.IBaseDAO;
import com.ksproyectos.parking4all.core.DAO.IMovimientosDAO;
import com.ksproyectos.parking4all.core.DAO.MovimientosQueries;
import com.ksproyectos.parking4all.core.Entities.IMovimientos;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kevin
 */
public class MovimientosRepository  extends AbstractBaseRepositor<IMovimientos>{
    
    IMovimientosDAO dao;
    
    public MovimientosRepository(IMovimientosDAO dao){    
        this.dao = dao;
    }
    
    public List<IMovimientos> getMovimientosByPlaca(String placa){
        
        IMovimientos movimiento = dao.getEntity();
        
        movimiento.setPlaca(placa);
        
        return dao.findAll(MovimientosQueries.GET_BY_PLACA, movimiento); 
    }
    
    public void addMovimiento(IMovimientos movimiento){
        dao.create(movimiento);
    }

    public void updateMovimiento(IMovimientos movimiento) {
        dao.update(movimiento);
    }
    
    public List<IMovimientos> getMovimientosSalidaPorRangoFecha(Date fechaDesde, Date fechaHasta, List<String> tiposTarifa){ 
        return dao.findAll(MovimientosQueries.GET_ALL_MOVIMIENTOS_SALIDA_BY_FECHA_DESDE_HASTA, fechaDesde, fechaHasta, tiposTarifa);
    }
    
    public List<IMovimientos> getMovimientosEntradaPorRangoFecha(Date fechaDesde, Date fechaHasta, List<String> tiposTarifa){ 
        return dao.findAll(MovimientosQueries.GET_ALL_MOVIMIENTOS_ENTRADA_BY_FECHA_DESDE_HASTA, fechaDesde, fechaHasta, tiposTarifa);
    }
    
    public void removeMovimiento(IMovimientos movimiento) {
        dao.delete(movimiento);
    }

    @Override
    public IBaseDAO getDAO() {
        return this.dao;
    }
    
}
