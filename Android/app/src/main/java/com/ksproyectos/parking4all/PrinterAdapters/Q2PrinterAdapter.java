package com.ksproyectos.parking4all.PrinterAdapters;

import android.content.Context;

import com.ksproyectos.parking4all.DriverFacades.Q2.Q2DriverFacade;
import com.ksproyectos.parking4all.TicketAdapters.Q2.TicketCierreCaja;
import com.ksproyectos.parking4all.TicketAdapters.Q2.TicketEntrada;
import com.ksproyectos.parking4all.TicketAdapters.Q2.TicketMensualidad;
import com.ksproyectos.parking4all.TicketAdapters.Q2.TicketSalida;

public class Q2PrinterAdapter extends AbstractPrinterAdapter{

    private final Context context;

    public Q2PrinterAdapter(Context context){
        this.context = context;
    }

    @Override
    public TicketEntrada getTicketEntrada() {
        Q2DriverFacade driverFacade = new Q2DriverFacade();
        driverFacade.setContext(context);
        return new TicketEntrada(driverFacade);
    }

    @Override
    public TicketSalida getTicketSalida() {
        Q2DriverFacade driverFacade = new Q2DriverFacade();
        driverFacade.setContext(context);
        return new TicketSalida(driverFacade);
    }

    @Override
    public TicketMensualidad getTicketMensualidad() {
        Q2DriverFacade driverFacade = new Q2DriverFacade();
        driverFacade.setContext(context);
        return new TicketMensualidad(driverFacade);
    }

    @Override
    public TicketCierreCaja getTicketCierreCaja() {
        Q2DriverFacade driverFacade = new Q2DriverFacade();
        driverFacade.setContext(context);
        return new TicketCierreCaja(driverFacade);
    }
}
