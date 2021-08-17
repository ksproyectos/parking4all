/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Services.Abstract;

import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.HashMap;

/**
 *
 * @author kevin
 */
public abstract class AbstractPrinterService {
        
    public enum Reporte{
        TICKET_ENTRADA,
        TICKET_SALIDA,
        TICKET_MENSUALIDAD,
        TICKET_CIERRE_CAJA;  
    }
        
    public abstract void imprimirAsync(Reporte reporte, HashMap parametros);
    
    public abstract void imprimirAsync(String nombreReporte, HashMap parametros);
    
    public abstract void imprimir(Reporte reporte, HashMap parametros) throws Parking4AllException;
    
    public abstract void imprimir(String nombreReporte, HashMap parametros) throws Parking4AllException;
    
}
