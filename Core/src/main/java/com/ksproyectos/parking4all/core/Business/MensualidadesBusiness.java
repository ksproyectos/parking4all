/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Business;

import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import com.ksproyectos.parking4all.core.Entities.ITarifas;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractAuthService;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractPrinterService;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author kevin
 */
public class MensualidadesBusiness {

    IMensualidades mensualidad;
    
    ITarifas tarifa;

    BusinessServiceProvider businessServiceProvider;
    
    CajaBusiness cajaBusiness;
    public MensualidadesBusiness(BusinessServiceProvider businessServiceProvider) {
        this.businessServiceProvider = businessServiceProvider;
        cajaBusiness = new CajaBusiness(businessServiceProvider);
    }

    public List<IMensualidades> obtenerMensualidadesActivas() {
        
        Calendar fechaLimite = Calendar.getInstance();
        fechaLimite.add(Calendar.DATE, -30);

        return businessServiceProvider
                .getRepositoryService()
                .getMensualidadesRepository()
                .getMensualidadesBy(fechaLimite.getTime(), new Date());
    }

    public void eliminarMensualidad(IMensualidades mensualidad) throws Parking4AllException {
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.DELETE_MENSUALIDADES)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        businessServiceProvider
                .getRepositoryService()
                .getMensualidadesRepository()
                .removeMensualidad(mensualidad);

    }

    public void crearMensualidad(
            String nombres
            , String numeroDocumento
            , String placa
            , String telefono
            , String tipoTarifa) throws Parking4AllException {
        
        
        mensualidad =  businessServiceProvider
                    .getRepositoryService()
                    .getMensualidadesRepository().getEntity();
        
        mensualidad.setFechaPago(new Date());
        mensualidad.setNombres(nombres);
        mensualidad.setNumeroDocumento(numeroDocumento);
        mensualidad.setPlaca(placa);
        mensualidad.setTelefono(telefono);
        
        try{
            
            tarifa = this.businessServiceProvider
                .getRepositoryService()
                 .getTarifasRepository()
                 .getTarifaMesByTipoTarifa(tipoTarifa);
        
        }catch(Exception e){
            throw new Parking4AllException("Error al consultar la tarifa mensual para " + tipoTarifa, e);  
        }

        if(null == tarifa){
            throw new Parking4AllException("No existe una tarifa de 30 dias configurada para el tipo de tarifa: " + tipoTarifa);
        }
        
        mensualidad.setValorTotal(tarifa.getTarifa());

        this.businessServiceProvider.getRepositoryService().executeTransaction((param) -> {
            
            cajaBusiness.sumarValorACaja(mensualidad.getValorTotal());
                
            businessServiceProvider
                    .getRepositoryService()
                    .getMensualidadesRepository()
                    .addMensualidad(mensualidad);
            
            return true;
        });

    }
    
    public void imprimirTicketMensualidad(){  
        HashMap parametros = new HashMap();
        parametros.put("mensualidad", mensualidad);
       this.businessServiceProvider.getPrinterService().imprimirAsync(AbstractPrinterService.Reporte.TICKET_MENSUALIDAD, parametros);

    }
    
    public List<String> getListaTarifasDisponibles(){
        return this.businessServiceProvider.getRepositoryService().getCustomQueriesRepository()
                .getAllTiposTarifa();
    }

}
