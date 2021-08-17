package com.ksproyectos.parking4all.core.Business;

import com.ksproyectos.parking4all.core.DAO.DAOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.ksproyectos.parking4all.core.Entities.ICaja;
import com.ksproyectos.parking4all.core.Entities.IMovimientos;
import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import com.ksproyectos.parking4all.core.Entities.ITarifas;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.HashMap;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractPrinterService;
import com.ksproyectos.parking4all.core.Utils.DateUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class MainBusiness {
    
    private final static Logger LOGGER = Logger.getLogger(MainBusiness.class.getName());

    BusinessServiceProvider businessServiceProvider;
    
    //objetos de negocio
    
    private IMovimientos movimiento;
    
    private List<IMovimientos> movimientosResult = new ArrayList<IMovimientos>();
    
    //fin objetos de negocio
    
    
    public MainBusiness(BusinessServiceProvider businessServiceProvider){
     
        this.businessServiceProvider = businessServiceProvider;
            
        this.movimiento = this.businessServiceProvider.getRepositoryService().getMovimientosRepository().getEntity();
        
    }
    
    // encapsulamiento
    
    public String getPlaca(){
        return this.movimiento.getPlaca();
    }
    
    public Date getFechaEntrada(){
        return this.movimiento.getFechaEntrada();
    }
    
    public String getTipoTarifa(){
        return this.movimiento.getTipoTarifa();
    }
    
    public int getValorTotal(){
        return this.movimiento.getValorTotal();
    }
    
    // fin encapsulamiento
        
    //procesos de negocio
    public boolean consultarPlaca(String placa){
        
        if(placa.length() < 6){
            return false;
        }
        
        movimientosResult.clear();
        
        movimientosResult = this.businessServiceProvider.getRepositoryService().getMovimientosRepository().getMovimientosByPlaca(placa);
        
        if(!movimientosResult.isEmpty()){
            
            movimiento = movimientosResult.get(0);
            
            return true;
            
        }else{
            
            movimiento = null;
            
            return false;
        }
        
    }
    
    public void registrarSalida() throws Parking4AllException{

        calcularTotal(
                this.movimiento.getTipoTarifa(), 
                this.movimiento.getFechaEntrada(), 
                new Date(), 
                this.movimiento.getPlaca());

        Date fechaActual = new Date();
        movimiento.setFechaSalida(fechaActual);
        movimiento.setFinalizado(true);
        movimiento.setIdUsuarioSalida(businessServiceProvider.getAuthService().getIdUsuarioAutenticado());

        /* Actualiza la caja */
        ICaja caja;
        caja = this.businessServiceProvider.getRepositoryService().getCajaRepository().getCajaActiva();

        caja.setTotal(caja.getTotal() + movimiento.getValorTotal());
        this.businessServiceProvider.getRepositoryService().executeTransaction((param)->{

            this.businessServiceProvider.getRepositoryService().getCajaRepository().updateCaja(caja);
            this.businessServiceProvider.getRepositoryService().getMovimientosRepository().updateMovimiento(movimiento);

            return true;
        });
    }
    
    public void registrarEntrada(String placa, String tipoTarifa) {
        movimiento = this.businessServiceProvider.getRepositoryService().getMovimientosRepository().getEntity();
        movimiento.setFechaEntrada(new Date());

        movimiento.setPlaca(placa);
        movimiento.setTipoTarifa(tipoTarifa);
        movimiento.setIdUsuarioEntrada(businessServiceProvider.getAuthService().getIdUsuarioAutenticado());
        movimiento.setFinalizado(Boolean.FALSE);

        this.businessServiceProvider.getRepositoryService().getMovimientosRepository().addMovimiento(movimiento);

    }

    public void registrarEntradaPrepagada(String placa, ITarifas tarifa) throws Parking4AllException {
        movimiento = this.businessServiceProvider.getRepositoryService().getMovimientosRepository().getEntity();
        movimiento.setFechaEntrada(new Date());

        movimiento.setPlaca(placa);
        movimiento.setTipoTarifa(tarifa.getTipoTarifa());
        movimiento.setIdUsuarioEntrada(businessServiceProvider.getAuthService().getIdUsuarioAutenticado());
        movimiento.setFinalizado(Boolean.TRUE);

        Date fechaActual = new Date();
        movimiento.setFechaSalida(fechaActual);
        movimiento.setFinalizado(true);
        movimiento.setIdUsuarioSalida(businessServiceProvider.getAuthService().getIdUsuarioAutenticado());

        movimiento.setValorTotal(tarifa.getTarifa());

        /* Actualiza la caja */
        ICaja caja;
        caja = this.businessServiceProvider.getRepositoryService().getCajaRepository().getCajaActiva();

        caja.setTotal(caja.getTotal() + movimiento.getValorTotal());
        this.businessServiceProvider.getRepositoryService().executeTransaction((param)->{

            this.businessServiceProvider.getRepositoryService().getCajaRepository().updateCaja(caja);
            this.businessServiceProvider.getRepositoryService().getMovimientosRepository().updateMovimiento(movimiento);

            return true;
        });

    }
    
    public void imprimirTicketSalida() {
        HashMap<String, Object> parametros = new HashMap();
        
        parametros.put("idMovimiento", movimiento.getIdMovimiento());
        
        this.businessServiceProvider.getPrinterService().imprimirAsync(AbstractPrinterService.Reporte.TICKET_SALIDA, parametros);
        
    }

    public void imprimirTicketEntrada() {
        
        HashMap<String, Object> parametros = new HashMap();
        
        parametros.put("idMovimiento", movimiento.getIdMovimiento());
        
        this.businessServiceProvider.getPrinterService().imprimirAsync(AbstractPrinterService.Reporte.TICKET_ENTRADA, parametros);
        
    }
    
    //fin procesos negocio
    
    //reglas de negocio
    
    public void calcularTotal(Date fechaFinal){
        
        calcularTotal(
                this.movimiento.getTipoTarifa(),
                this.movimiento.getFechaEntrada(),
                fechaFinal,
                this.movimiento.getPlaca());
    }
    
    public void calcularTotal(String tipoTarifa, Date fechaEntrada,
            Date fechaSalida,  String placa) {
        
        Long dias = DateUtils.getDateDiffDays(fechaEntrada, fechaSalida);

        Long horas = DateUtils.getDateDiffHours(fechaEntrada, fechaSalida);

        Long minutos = DateUtils.getDateDiffMinutes(fechaEntrada, fechaSalida);

        int totalDias = 0;
        int totalHoras = 0;
        int totalMinutos = 0;
        
        Long horasAdicionales = 0L;
        Long minutosAdicionales = 0L;
        
        Long diasParaCalculo = dias;
                
        Long minutosParaCalculo = minutos;
        Long horasParaCalulo = horas;
        
        
        List<ITarifas> tarifasDia =
                    this.businessServiceProvider.getRepositoryService().
                            getTarifasRepository()
                            .getAllTarifasDiaByTipoTarifa(tipoTarifa);
        List<ITarifas> tarifasHora = 
                    this.businessServiceProvider.getRepositoryService().
                            getTarifasRepository()
                            .getAllTarifasHoraByTipoTarifa(tipoTarifa);
        List<ITarifas> tarifasMinuto =  
                    this.businessServiceProvider.getRepositoryService()
                            .getTarifasRepository()
                            .getAllTarifasMinutoByTipoTarifa(tipoTarifa);
        
        Iterator<ITarifas> iterator;
        
        if(diasParaCalculo > 0){
            
            if(tarifasDia.isEmpty()){
                
                horasAdicionales = diasParaCalculo * 24;
                                
            }else{
                
                iterator = tarifasDia.iterator();

                Long diasRestantes = diasParaCalculo;
                
                while (diasRestantes > 0) {
                    
                    if(!iterator.hasNext()){                       
                        break;   
                    }else{
                    
                        ITarifas tarifaDia = iterator.next();
                        
                        if(tarifaDia.getDias() > diasRestantes){  
                            continue;                            
                        }else{
                            
                            int frecuencia = 
                                diasRestantes.intValue() / tarifaDia.getDias();

                            totalDias = 
                                    totalDias + (tarifaDia.getTarifa() * frecuencia);
                            diasRestantes = 
                                    diasRestantes - (tarifaDia.getDias() * frecuencia);
                            
                        }
                    
                    }
                }
                
                if(diasRestantes > 0){
                    horasAdicionales = diasRestantes*24;
                }
                
            }
            
        }
         
        
        horasParaCalulo = (horasParaCalulo + horasAdicionales);
        
        if(horasParaCalulo > 0){
                       
            if(tarifasHora.isEmpty()){
                
                minutosAdicionales = horasParaCalulo * 60;

            }else{
                
                iterator = tarifasHora.iterator();

                Long horasRestantes = horasParaCalulo;
                
                while (horasRestantes > 0) {

                    if(!iterator.hasNext()){
                        break;
                    }else{
                        ITarifas tarifaHora = iterator.next();
                    
                        if(tarifaHora.getTarifa() == 0){
                            horasRestantes = horasRestantes - tarifaHora.getHoras();
                            continue;
                        }

                        if(tarifaHora.getHoras()> horasRestantes){
                            continue;
                        }else{
                            
                                int frecuencia =
                                    horasRestantes.intValue() / tarifaHora.getHoras();
                            totalHoras = 
                                    totalHoras + (tarifaHora.getTarifa() * frecuencia);
                            horasRestantes = 
                                    horasRestantes - (tarifaHora.getHoras() * frecuencia);
                            
                        }
                    } 
                }
                
                iterator = tarifasDia.iterator();
                while(iterator.hasNext()){
                    ITarifas tarifaDia = (ITarifas) iterator.next();
                    if(tarifaDia.getDias() == 1){
                        
                        if (tarifaDia.getTarifa() < totalHoras) {
                            totalHoras = tarifaDia.getTarifa();

                            diasParaCalculo = 0L;
                            horasParaCalulo = 0L;
                            minutosParaCalculo = 0L;

                            horasRestantes = 0L;
                        
                        }
                        break;
                    }
                }

                if(horasRestantes > 0){
                    minutosAdicionales = horasRestantes*60;
                }
            }  
            
        }
        
        minutosParaCalculo = minutosParaCalculo + minutosAdicionales;
        
        if( minutosParaCalculo > 0){
            
            iterator = tarifasMinuto.iterator();
            
            Long minutosRestantes = minutosParaCalculo;
            while (minutosRestantes > 0) {
                
                if(!iterator.hasNext()){
                    break;
                }else{
                    
                    ITarifas tarifaMinuto = iterator.next();    
                
                    if(tarifaMinuto.getTarifa() == 0){
                        minutosRestantes = 
                                minutosRestantes - tarifaMinuto.getMinutos();
                        continue;
                    }

                    if(tarifaMinuto.getMinutos() > minutosRestantes){
                        continue;
                    }else{
                    
                        int frecuencia = 
                            (int) Math.floor(minutosRestantes.intValue() / tarifaMinuto.getMinutos());
                        totalMinutos = 
                                totalMinutos + (tarifaMinuto.getTarifa() * frecuencia);
                        minutosRestantes = 
                                minutosRestantes 
                                - (tarifaMinuto.getMinutos() * frecuencia);
                    
                    }
                }
            }
            
            iterator = tarifasHora.iterator();
            while(iterator.hasNext()){
                ITarifas tarifa = (ITarifas) iterator.next();
                if(tarifa.getHoras() == 1){
                    if (tarifa.getTarifa() < totalMinutos) {
                        totalMinutos = tarifa.getTarifa();
                        minutosParaCalculo = 0L;
                    }
                    break;
                }
            }
        }

/*-----------------------------Calculo 2---------------------------------- */       

        diasParaCalculo = dias;
        horasParaCalulo = horas;
        minutosParaCalculo = minutos;

        if(diasParaCalculo > 0 ){
            iterator = tarifasDia.iterator();
            while(iterator.hasNext()){
                ITarifas tarifa = (ITarifas) iterator.next();
                if(tarifa.getDias() > diasParaCalculo){
                    if (tarifa.getTarifa() < ( totalDias + totalMinutos + totalHoras)) {
                        totalDias = tarifa.getTarifa();
                        totalHoras = 0;
                        totalMinutos = 0;

                        diasParaCalculo = 0L;
                        horasParaCalulo = 0L;
                        minutosParaCalculo = 0L;
                    }
                    break;
                }
            }
        
        }
        
        if(horasParaCalulo > 0){         
            iterator = tarifasHora.iterator();
            while(iterator.hasNext()){
                ITarifas tarifa = (ITarifas) iterator.next();
                if(tarifa.getHoras() > horasParaCalulo){
                    if (tarifa.getTarifa() < (totalHoras + totalMinutos)) {
                        totalHoras = tarifa.getTarifa();
                        totalMinutos = 0;

                        minutosParaCalculo = 0L;
                    }
                    break;
                }
            }  
            
        }
        
        if(minutosParaCalculo > 0){
            iterator = tarifasMinuto.iterator();
            while(iterator.hasNext()){
                ITarifas tarifa = (ITarifas) iterator.next();
                if(tarifa.getMinutos() > minutosParaCalculo){
                     if (tarifa.getTarifa() < totalMinutos) {
                        totalMinutos = tarifa.getTarifa();
                    }
                    break;
                }
            }  
            
        }
        
/*-----------------------------Mensualidad---------------------------------- */       
       

        IMensualidades mensualidad = this.businessServiceProvider.getRepositoryService()
            .getMensualidadesRepository()
            .getMensualidadActivaByPlaca(placa);
        if(null != mensualidad){
            if (mensualidad.getPlaca().equals(placa)) {
                totalDias = 0;
                totalHoras = 0;
                totalMinutos = 0;
            }
        }
        movimiento.setValorTotal(totalDias + totalHoras + totalMinutos);

    }
    
    // fin reglas negocio
    
    // servicios negocio
    
    public List<String> getListaTarifasDisponibles(){
        return this.businessServiceProvider.getRepositoryService().getCustomQueriesRepository()
                .getAllTiposTarifa();
    }

    public List<ITarifas> getListaTarifasPrepagadasActivas(){
        return this.businessServiceProvider.getRepositoryService().getTarifasRepository().getAllTarifasPrepagadasActivas();
    }
    
    // fin servicios negocio
    
}
