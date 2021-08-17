/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.Services;

import android.app.Activity;
import android.os.AsyncTask;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Caja;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.core.Entities.ICaja;
import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractPrinterService;
import com.ksproyectos.parking4all.core.Utils.DateUtils;
import com.leerybit.escpos.DeviceCallbacks;
import com.leerybit.escpos.PosPrinter;
import com.leerybit.escpos.PosPrinter80mm;
import com.leerybit.escpos.Ticket;
import com.leerybit.escpos.TicketBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author kevin
 */
public class PrinterServicebk extends AbstractPrinterService{

    private final static PrinterServicebk instance = new PrinterServicebk();

    private RepositoryService repositoryService = RepositoryService.getInstance();

    private static PosPrinter printer;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static Activity context;

    public static void setPrinter(Activity activity){

        context = activity;

        PrinterServicebk.printer = new PosPrinter80mm(activity);

        printer.setDeviceCallbacks(new DeviceCallbacks() {
            @Override
            public void onConnected() {

                System.out.println("ok");
            }

            @Override
            public void onFailure() {

                System.out.println("falló");
            }

            @Override
            public void onDisconnected() {

            }
        });

        if (printer.isConnected()){
            printer.disconnect();
        } else {
            printer.connect();
        }
    }

    private PrinterServicebk(){

    }

    public static PrinterServicebk getInstance(){
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



                Ticket ticketEntrada = new TicketBuilder(printer)
                        .header("Sistema POS Parking4All - www.facturaventa.com")
                        .divider()
                        .text("Ticket Preventa No: " + movimientoEntrada.getIdMovimiento())
                        .divider()
                        .subHeader("Detalle")
                        .text("PLACA: " + movimientoEntrada.getPlaca())
                        .text("FECHA ENTRADA:  " + df.format(movimientoEntrada.getFechaEntrada()))
                        .text("TARIFA: " + movimientoEntrada.getTipoTarifa())
                        .dividerDouble()
                        .stared("GRACIAS POR PREFERIRNOS")
                        .build();

                printer.send(ticketEntrada);


                break;

            case  TICKET_SALIDA:

                Movimientos movimientoSalida = new Movimientos();
                movimientoSalida.setIdMovimiento((int) parametros.get("idMovimiento"));

                movimientoSalida = (Movimientos) RepositoryService.getInstance().getMovimientosDAO().read(movimientoSalida);

                Ticket ticketSalida = new TicketBuilder(printer)
                        .header("Sistema POS Parking4All - www.facturaventa.com")
                        .divider()
                        .text("Ticket No: " + movimientoSalida.getIdMovimiento())
                        .divider()
                        .subHeader("Detalle")
                        .text("PLACA: " + movimientoSalida.getPlaca())
                        .text("FECHA ENTRADA:  " + df.format(movimientoSalida.getFechaEntrada()))
                        .text("FECHA SALIDA:  " + df.format(movimientoSalida.getFechaSalida()))
                        .text("TIEMPO: " +
                                DateUtils.getDateDiffDays(
                                        movimientoSalida.getFechaEntrada(),
                                        movimientoSalida.getFechaSalida()).toString() + " dias "
                                +
                                DateUtils.getDateDiffHours(
                                        movimientoSalida.getFechaEntrada(),
                                        movimientoSalida.getFechaSalida()).toString() + " horas "
                                +
                                DateUtils.getDateDiffMinutes(
                                        movimientoSalida.getFechaEntrada(),
                                        movimientoSalida.getFechaSalida()).toString() + " minutos ")
                        .fiscalDouble("Total: ", movimientoSalida.getValorTotal())
                        .dividerDouble()
                        .stared("GRACIAS POR PREFERIRNOS")
                        .build();
                printer.send(ticketSalida);

                break;
            case TICKET_CIERRE_CAJA:
                ICaja caja = new Caja();
                caja.setIdCaja((Integer) parametros.get("idCaja"));
                caja = RepositoryService.getInstance().getCajaDAO().read(caja);
                Ticket ticketCierreCaja = new TicketBuilder(printer)
                        .header("Sistema POS Parking4All - www.facturaventa.com")
                        .divider()
                        .text("Cierre de caja")
                        .divider()
                        .subHeader("Detalle")
                        .text("TOTAL: " + caja.getTotal())
                        .text("FECHA INICIO: " + df.format(caja.getFechaApertura()))
                        .text("FECHA FIN: " + df.format(caja.getFechaCierre()) )
                        .feedLine(3)
                        .build();
                printer.send(ticketCierreCaja);
                break;

            case TICKET_MENSUALIDAD:
                imprimirTicketMensualidad((IMensualidades) parametros.get("mensualidad"));
                break;
        }

    }

    public void imprimirTicketMensualidad(IMensualidades mensualidad){

        Calendar fechaLimite = Calendar.getInstance();
        fechaLimite.add(Calendar.DATE, -30);

        Ticket ticketMensualidad = new TicketBuilder(printer)
                .header("Sistema POS Parking4All - www.facturaventa.com")
                .divider()
                .text("Comprobante pago mensualidad")
                .divider()
                .subHeader("Detalle")
                .text("TOTAL: " + mensualidad.getValorTotal())
                .text("FECHA INICIO: " + df.format(mensualidad.getFechaPago()))
                .text("FECHA FIN: " + df.format(fechaLimite.getTime()) )
                .dividerDouble()
                .stared("GRACIAS POR PREFERIRNOS")
                .feedLine(3)
                .build();
        printer.send(ticketMensualidad);
    }

    public void imprimir(String nombreReporte, HashMap parametros) throws Parking4AllException{


    }
}
