/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.Services;

import com.ksproyectos.parking4all.DAO.Mysql.EntityManagerSingleton;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractPrinterService;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author kevin
 */
public class PrinterService extends AbstractPrinterService{
    
    private final static Logger LOGGER = Logger.getLogger(PrinterService.class.getName());
    
    private final static PrinterService instance = new PrinterService();
    
    private static EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

    private  PrinterService(){
        
    }
    
    public static PrinterService getInstance(){
        return instance;
    }
    
    public void imprimirAsync(Reporte reporte, HashMap parametros){
        Thread thread = new Thread(){
            public void run(){
                try {
                    imprimir(reporte, parametros);
                } catch (Parking4AllException ex) {
                    LOGGER.log(Level.ERROR, null, ex);
                }
            }
        };

        thread.start(); 
    }
    
    public void imprimirAsync(String nombreReporte, HashMap parametros){
        Thread thread = new Thread(){
            public void run(){
                try {
                    imprimir(nombreReporte, parametros);
                } catch (Parking4AllException ex) {
                    LOGGER.log(Level.ERROR, null, ex);
                }
            }
        };

        thread.start(); 
    }
    
    
    public void imprimir(Reporte reporte, HashMap parametros) throws Parking4AllException{
        
        try{
            /*Imprime el ticket */
            JasperReport jasperReport;

            jasperReport = (JasperReport) 
                    JRLoader.loadObject(new File(reporte.toString() + ".jasper"));

            JasperPrint jasperPrint = 
                    JasperFillManager.fillReport(jasperReport, 
                            parametros, 
                            (Connection) DriverManager.getConnection(
                                    (String) entityManager.getEntityManagerFactory().getProperties().get("javax.persistence.jdbc.url"),
                                    (String) entityManager.getEntityManagerFactory().getProperties().get("javax.persistence.jdbc.user"),
                                    (String) entityManager.getEntityManagerFactory().getProperties().get("javax.persistence.jdbc.password")));

            JasperPrintManager.printReport(jasperPrint, false);
        } catch (JRException ex) {
            LOGGER.log(Level.ERROR, null, ex);
            throw new Parking4AllException("Upps!! Ha habido un error al crear el ticket. Verifique que los tickets existan en el directorio de reportes.", ex);
            
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, ex);
            throw new Parking4AllException("Upps!! Ha habido un error al crear el ticket. \n No se pudo obtener información de la base de datos. \n Verifique que la base de datos esté disponible.", ex);
        } catch (Exception ex){
            throw new Parking4AllException("Upps!! Ha habido un error al crear el ticket. Error desconocido.", ex);
        }
        
    }
    
    public void imprimir(String nombreReporte, HashMap parametros) throws Parking4AllException{
        
        
    }
    
}
