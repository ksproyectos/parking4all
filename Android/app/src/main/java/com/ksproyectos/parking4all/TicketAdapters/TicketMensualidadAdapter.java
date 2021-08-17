package com.ksproyectos.parking4all.TicketAdapters;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Mensualidades;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

public interface TicketMensualidadAdapter {
    void imprimir(Mensualidades mensualidad) throws Parking4AllException;
}
