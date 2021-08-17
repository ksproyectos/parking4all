package com.ksproyectos.parking4all.TicketAdapters;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

public interface TicketEntradaAdapter {
    void imprimir(Movimientos movimientoEntrada) throws Parking4AllException;
}
