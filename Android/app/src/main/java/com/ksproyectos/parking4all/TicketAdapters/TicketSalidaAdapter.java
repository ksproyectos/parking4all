package com.ksproyectos.parking4all.TicketAdapters;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

public interface TicketSalidaAdapter {
    void imprimir(Movimientos movimientoSalida) throws Parking4AllException;
}
