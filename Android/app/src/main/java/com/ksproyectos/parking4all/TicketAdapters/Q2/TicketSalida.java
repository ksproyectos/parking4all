package com.ksproyectos.parking4all.TicketAdapters.Q2;

import com.ksproyectos.parking4all.Constants.Global;
import com.ksproyectos.parking4all.Constants.Messages;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.DriverFacades.Q2.Q2DriverFacade;
import com.ksproyectos.parking4all.TicketAdapters.TicketEntradaAdapter;
import com.ksproyectos.parking4all.TicketAdapters.TicketSalidaAdapter;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import java.text.SimpleDateFormat;

public class TicketSalida implements TicketSalidaAdapter {

    private final Q2DriverFacade driverFacade;

    SimpleDateFormat df = new SimpleDateFormat(Global.PRINT_DATE_FORMAT);


    public TicketSalida(Q2DriverFacade driverFacade){
        this.driverFacade = driverFacade;
    }

    @Override
    public void imprimir(Movimientos movimiento) throws Parking4AllException {

        try{
            driverFacade.openPrinter();

            driverFacade.setTextFormat(2,0,0);


            driverFacade.printCenteredText("CENTRO COMERCIAL");

            driverFacade.printCenteredText("LOS MOLINOS");


            driverFacade.setTextFormat(0,0,0);


            driverFacade.printText("FACTURA # " + movimiento.getIdMovimiento());

            driverFacade.printText("PLACA: " + movimiento.getPlaca());

            driverFacade.printText("TIPO TARIFA: " + movimiento.getTipoTarifa());

            driverFacade.printText("FECHA ENTRADA: " + df.format(movimiento.getFechaEntrada()));

            driverFacade.printText("FECHA SALIDA: " + df.format(movimiento.getFechaSalida()));

            driverFacade.printText("USUARIO ENTRADA: " + movimiento.getIdUsuarioEntrada());

            driverFacade.printText("USUARIO SALIDA: " + movimiento.getIdUsuarioSalida());

            driverFacade.printText("El Centro Comercial LOS MOLINOS PH presta el servicio de estacionamiento, el cual se encuentra sometido al siguiente reglamento en el que se notifican y comunican las políticas de uso que se deben cumplir al ingresar a nuestras instalaciones.");

            driverFacade.printLineBreaks(5);

            driverFacade.closePrinter();
        }catch (Exception e){

            throw new Parking4AllException(Messages.ERR0001);

        }


    }
}
