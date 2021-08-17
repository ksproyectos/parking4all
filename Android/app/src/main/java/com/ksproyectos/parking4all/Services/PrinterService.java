/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.Services;

import android.os.AsyncTask;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Caja;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Mensualidades;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.PrinterAdapters.AbstractPrinterAdapter;
import com.ksproyectos.parking4all.core.Entities.ICaja;
import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractPrinterService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author kevin
 */
public class PrinterService extends AbstractPrinterService{

    private final static PrinterService instance = new PrinterService();

    private RepositoryService repositoryService = RepositoryService.getInstance();

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static AbstractPrinterAdapter printer;

    public void setPrinterAdapter(AbstractPrinterAdapter printerAdapter){

        printer = printerAdapter;

    }

    private  PrinterService(){

    }

    public static PrinterService getInstance(){
        return instance;
    }

    public void imprimirAsync(Reporte reporte, HashMap parametros){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    imprimir(reporte, parametros);
                }catch (Parking4AllException e){

                }
            }
        });
    }

    public void imprimirAsync(String nombreReporte, HashMap parametros){


    }

    public void imprimir(Reporte reporte, HashMap parametros) throws Parking4AllException{

        switch (reporte){
            case  TICKET_ENTRADA:

                Movimientos movimientoEntrada = new Movimientos();
                movimientoEntrada.setIdMovimiento((int) parametros.get("idMovimiento"));

                movimientoEntrada = (Movimientos) RepositoryService.getInstance().getMovimientosDAO().read(movimientoEntrada);



                printer.getTicketEntrada().imprimir(movimientoEntrada);


                break;

            case  TICKET_SALIDA:

                Movimientos movimientoSalida = new Movimientos();
                movimientoSalida.setIdMovimiento((int) parametros.get("idMovimiento"));

                movimientoSalida = (Movimientos) RepositoryService.getInstance().getMovimientosDAO().read(movimientoSalida);


                printer.getTicketSalida().imprimir(movimientoSalida);


                break;
            case TICKET_CIERRE_CAJA:
                Caja caja = new Caja();
                caja.setIdCaja((Integer) parametros.get("idCaja"));

                caja = (Caja) RepositoryService.getInstance().getCajaDAO().read(caja);

                printer.getTicketCierreCaja().imprimir(caja);

                break;

            case TICKET_MENSUALIDAD:

                Mensualidades mensualidad = (Mensualidades) parametros.get("mensualidad");


                printer.getTicketMensualidad().imprimir(mensualidad);

                break;
        }

    }

    public void imprimir(String nombreReporte, HashMap parametros) throws Parking4AllException{


    }
}
