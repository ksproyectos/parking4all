/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Business;

import com.ksproyectos.parking4all.core.Entities.ICaja;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractPrinterService;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 *
 * @author kevin
 */
public class CajaBusiness {
   
    ICaja caja;
    
    BusinessServiceProvider businessServiceProvider;
    
    public CajaBusiness(BusinessServiceProvider businessServiceProvider){
        this.businessServiceProvider = businessServiceProvider;
    }
    
    
    public void consultarCaja() throws Parking4AllException{
        try {
            this.caja = businessServiceProvider
                    .getRepositoryService()
                    .getCajaRepository()
                    .getCajaActiva();
        } catch (Exception e) {
            throw new Parking4AllException("Error al consultar la caja activa.", e);
        }
        
        if(null == caja){
            throw new Parking4AllException("No existe una caja configurada.");
        }
    }
    
    public void sumarValorACaja(int valor) throws Parking4AllException{
        
        consultarCaja();
        if(null == caja){
            throw new Parking4AllException("No existe una caja configurada.");
        }
        
        caja.setTotal(caja.getTotal() + valor);
        
        try {
            this.businessServiceProvider
                    .getRepositoryService()
                    .getCajaRepository()
                    .updateCaja(caja);
        } catch(Exception e){
            throw new Parking4AllException("Error al actualizar la caja con el nuevo valor: " + caja.getTotal());
        }
    }
    
    
    public void restarValorACaja(int valor) throws Parking4AllException{
        
        consultarCaja();
         
        if(null == caja){
            throw new Parking4AllException("No existe una caja configurada.");
        }
        
        caja.setTotal(caja.getTotal() - valor);
        try{
            this.businessServiceProvider
                    .getRepositoryService()
                    .getCajaRepository()
                    .updateCaja(caja);
         } catch(Exception e){
            throw new Parking4AllException("Error al actualizar la caja con el nuevo valor: " + caja.getTotal());
        }
    }
    
    public Integer getTotal(){
        return caja.getTotal();
    }
    
    public Date getFechaApertura(){
        return caja.getFechaApertura();
    }
    
    public Integer getIdUsuarioApertura(){
        return caja.getIdUsuarioApertura();
    }
    
    public Date getFechaCierre(){
        return caja.getFechaCierre();
    }
    
    public Integer getIdUsuarioCierre(){
        return caja.getIdUsuarioCierre();
    }
    
    public boolean isCerrada(){
        return caja.isCerrada();
    }
    
    public void cerrarCaja(boolean isPrintTicketMandatory){
        
        try{
            consultarCaja();
            caja.setFechaCierre(new Date());
            caja.setCerrada(Boolean.TRUE);
            caja.setIdUsuarioCierre(businessServiceProvider.getAuthService().getIdUsuarioAutenticado());
            this.businessServiceProvider.getRepositoryService().executeTransaction((param)->{                    
                businessServiceProvider
                        .getRepositoryService()
                        .getCajaRepository()
                        .updateCaja(caja);

                int idCajaCerrada = caja.getIdCaja();

                this.caja = businessServiceProvider
                        .getRepositoryService()
                        .getCajaRepository().getEntity();

                this.caja.setCerrada(false);
                this.caja.setFechaApertura(new Date());
                this.caja.setIdUsuarioApertura(businessServiceProvider.getAuthService().getIdUsuarioAutenticado());
                this.caja.setTotal(0);

                businessServiceProvider
                        .getRepositoryService()
                        .getCajaRepository()
                        .createCaja(caja);

                if(isPrintTicketMandatory){
                    try{
                        imprimirCierreCaja(idCajaCerrada);
                    }catch(Parking4AllException e ){
                        onCerrarCajaListener.onCerrarCajaFailedPrint();
                        return false;
                    }
                }
                onCerrarCajaListener.onCerrarCajaSuccess();
                return true;
            });
        }catch(Exception e){
            onCerrarCajaListener.onCerrarCajaFailed(e.getMessage());
        }
    }
    
    
    
    private OnCerrarCajaListener onCerrarCajaListener;
    
    public static class StatusCode{
        public final static Integer FAIL_PRINT = 0;
        public final static Integer FAIL = 1;
        public final static Integer SUCCESS = 2;
    }
    
    public class Status{
        public Integer statusCode;
        public String message;
    }
    
    public void setOnCerrarCajaListener(OnCerrarCajaListener listener){
        this.onCerrarCajaListener = listener;
    }
    

    
    public void imprimirCierreCaja(int idCaja) throws Parking4AllException{
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("idCaja", idCaja);
        businessServiceProvider.getPrinterService().imprimir(AbstractPrinterService.Reporte.TICKET_CIERRE_CAJA, parametros);
    }

    public interface OnCerrarCajaListener {

        void onCerrarCajaFailedPrint();

        void onCerrarCajaFailed(String errorMessage);

        void onCerrarCajaSuccess();
    }
}
