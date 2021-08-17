package com.ksproyectos.parking4all.DAO.SQL;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.ksproyectos.parking4all.DAO.SQL.Contracts.Parking4AllContract;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Caja;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Configuraciones;
import com.ksproyectos.parking4all.DAO.SQL.Entities.DateConverter;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Mensualidades;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Tarifas;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Usuarios;
import com.ksproyectos.parking4all.DAO.SQL.RoomDAO.CajaRoomDAO;
import com.ksproyectos.parking4all.DAO.SQL.RoomDAO.CustomQueriesRoomDAO;
import com.ksproyectos.parking4all.DAO.SQL.RoomDAO.MensualidadesRoomDAO;
import com.ksproyectos.parking4all.DAO.SQL.RoomDAO.MovimientosRoomDAO;
import com.ksproyectos.parking4all.DAO.SQL.RoomDAO.TarifasRoomDAO;
import com.ksproyectos.parking4all.DAO.SQL.RoomDAO.TransactionalRoomDAO;
import com.ksproyectos.parking4all.DAO.SQL.RoomDAO.UsuariosRoomDAO;


@Database(version = 1, entities = {Movimientos.class, Caja.class, Configuraciones.class, Mensualidades.class, Tarifas.class, Usuarios.class})
@TypeConverters({DateConverter.class})
public abstract class Parking4AllDatabase extends RoomDatabase {

    public abstract MovimientosRoomDAO movimientosRoomDAO();
    public abstract TransactionalRoomDAO transactionalRoomDAO();
    public abstract TarifasRoomDAO tarifasRoomDAO();
    public abstract CustomQueriesRoomDAO customQueriesRoomDAO();
    public abstract CajaRoomDAO cajaRoomDAO();
    public abstract MensualidadesRoomDAO mensualidadesRoomDAO();
    public abstract UsuariosRoomDAO usuariosRoomDAO();



}