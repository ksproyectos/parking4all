package com.ksproyectos.parking4all.DAO.SQL.RoomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Mensualidades;

import java.util.Date;
import java.util.List;


@Dao
public abstract class MensualidadesRoomDAO {

    @Query("SELECT * FROM Mensualidades WHERE idMensualidad = :idMensualidad")
    public abstract Mensualidades getMensualidadById(Integer idMensualidad);

    @Query("SELECT * From Mensualidades "
            + " WHERE placa = :placa"
            + " AND fechaPago"
            + " BETWEEN  :fechaPagoDesde AND :fechaPagoHasta")
    public abstract Mensualidades getMensualidadByPlaca(String placa, Date fechaPagoDesde, Date fechaPagoHasta);

    @Query("SELECT * FROM Mensualidades "
            + " WHERE fechaPago"
            + " BETWEEN  :fechaPagoDesde AND :fechaPagoHasta")
    public abstract List<Mensualidades> getAllMensualidadesByFechaPagoDesdeHasta(Long fechaPagoDesde, Long fechaPagoHasta);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void add(Mensualidades... mensualidades);

    @Update
    public abstract void update(Mensualidades... mensualidades);

    @Delete
    public abstract void delete(Mensualidades... Mensualidades);
}

