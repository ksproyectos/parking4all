/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Business;

import com.ksproyectos.parking4all.core.Entities.IMovimientos;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractAuthService;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kevin
 */
public class MovimientosBusiness {
    
    public enum TipoMovimiento{
        ENTRADA,
        SALIDA
    }
    
    BusinessServiceProvider businessServiceProvider;
    
    public MovimientosBusiness(BusinessServiceProvider businessServiceProvider) {
        this.businessServiceProvider = businessServiceProvider;
    }
    
    public List<IMovimientos> obtenerMovimientosPorRangoFecha(Date fechaDesde, Date fechaHasta, List<String> tiposTarifa, TipoMovimiento tipoMovimeinto ) throws Parking4AllException{
    
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.LIST_MOVIMIENTOS)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        if(tipoMovimeinto == TipoMovimiento.ENTRADA){
        return businessServiceProvider
                .getRepositoryService()
                .getMovimientosRepository()
                .getMovimientosEntradaPorRangoFecha(fechaDesde, fechaHasta, tiposTarifa);
        }else{
        
            return businessServiceProvider
                .getRepositoryService()
                .getMovimientosRepository()
                .getMovimientosSalidaPorRangoFecha(fechaDesde, fechaHasta, tiposTarifa);
        
        }
    }
    
    public void eliminarMovimiento(IMovimientos movimiento) throws Parking4AllException{  
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.DELETE_MOVIMIENTOS)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        businessServiceProvider
                .getRepositoryService()
                .getMovimientosRepository()
                .removeMovimiento(movimiento);
    }
    
}
