package com.ksproyectos.parking4all.TicketAdapters.Q2;

import com.ksproyectos.parking4all.Constants.Global;
import com.ksproyectos.parking4all.Constants.Messages;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Caja;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.DriverFacades.Q2.Q2DriverFacade;
import com.ksproyectos.parking4all.TicketAdapters.TicketCierreCajaAdapter;
import com.ksproyectos.parking4all.TicketAdapters.TicketSalidaAdapter;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import java.text.SimpleDateFormat;

public class TicketCierreCaja implements TicketCierreCajaAdapter {

    private final Q2DriverFacade driverFacade;

    SimpleDateFormat df = new SimpleDateFormat(Global.PRINT_DATE_FORMAT);


    public TicketCierreCaja(Q2DriverFacade driverFacade){
        this.driverFacade = driverFacade;
    }

    @Override
    public void imprimir(Caja caja) throws Parking4AllException {

        try{
            driverFacade.openPrinter();

            driverFacade.printCenteredText("CIERRE CAJA");

            driverFacade.printText("FECHA APERTURA: " + caja.getFechaApertura());

            driverFacade.printText("FECHA CIERRE: " + caja.getFechaCierre());

            driverFacade.printText("TOTAL: " + caja.getTotal());

            driverFacade.printLineBreaks(5);

            driverFacade.closePrinter();
        }catch (Exception e){

            throw new Parking4AllException(Messages.ERR0001);

        }


    }
}
