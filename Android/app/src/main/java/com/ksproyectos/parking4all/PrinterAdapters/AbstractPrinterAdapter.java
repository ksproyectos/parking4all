package com.ksproyectos.parking4all.PrinterAdapters;

import com.ksproyectos.parking4all.TicketAdapters.Q2.TicketCierreCaja;
import com.ksproyectos.parking4all.TicketAdapters.Q2.TicketEntrada;
import com.ksproyectos.parking4all.TicketAdapters.Q2.TicketMensualidad;
import com.ksproyectos.parking4all.TicketAdapters.Q2.TicketSalida;

public abstract class AbstractPrinterAdapter {

    public abstract TicketEntrada getTicketEntrada();

    public abstract TicketSalida getTicketSalida();

    public abstract TicketMensualidad getTicketMensualidad();

    public abstract TicketCierreCaja getTicketCierreCaja();



}
