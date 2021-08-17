package com.ksproyectos.parking4all.TicketAdapters;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Caja;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

public interface TicketCierreCajaAdapter {
    void imprimir(Caja caja) throws Parking4AllException;
}
