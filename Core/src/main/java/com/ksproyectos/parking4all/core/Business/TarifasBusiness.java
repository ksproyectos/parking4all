/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Business;

import com.ksproyectos.parking4all.core.Entities.ITarifas;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractAuthService;
import java.util.List;

/**
 *
 * @author kevin
 */
public class TarifasBusiness {

    private final BusinessServiceProvider businessServiceProvider;
    
    public TarifasBusiness(BusinessServiceProvider businessServiceProvider) {
        this.businessServiceProvider = businessServiceProvider;
    }
     
    public void crearTarifa(
        ITarifas tarifa
    ) throws Parking4AllException{
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.CREATE_TARIFAS)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        businessServiceProvider
                .getRepositoryService()
                .getTarifasRepository()
                .addTarifa(tarifa);
    }
    
    public List<ITarifas> obtenerTarifas() throws Parking4AllException{
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.LIST_TARIFAS)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        return businessServiceProvider
                .getRepositoryService()
                .getTarifasRepository()
                .getAllTarifas();
    }
    
    public void eliminarTarifa(ITarifas tarifa) throws Parking4AllException{
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.DELETE_MENSUALIDADES)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        businessServiceProvider
                .getRepositoryService()
                .getTarifasRepository()
                .removeTarifa(tarifa);
    
    }
    
    public List<String> getListaTarifasDisponibles(){
        return this.businessServiceProvider.getRepositoryService().getCustomQueriesRepository()
                .getAllTiposTarifa();
    }
    
}
