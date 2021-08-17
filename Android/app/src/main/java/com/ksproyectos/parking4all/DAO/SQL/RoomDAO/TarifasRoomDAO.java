package com.ksproyectos.parking4all.DAO.SQL.RoomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Tarifas;

import java.util.List;

@Dao
public abstract class TarifasRoomDAO {

    @Query("SELECT * FROM Tarifas t")
    public abstract List<Tarifas> getAllTarifas();

    @Query("SELECT * From Tarifas t"
            + " WHERE tipoTarifa = :tipoTarifa"
            + " AND dias > 0"
            + " ORDER BY t.dias desc")
    public abstract List<Tarifas> getAllTarifasDiaByTipoTarifa(String tipoTarifa);

    @Query("SELECT *  From Tarifas "
            + " WHERE tipoTarifa = :tipoTarifa"
            + " AND horas > 0"
            + " ORDER BY dias desc")
    public abstract List<Tarifas> getAllTarifasHoraByTipoTarifa(String tipoTarifa);

    @Query("SELECT * From Tarifas"
            + " WHERE tipoTarifa = :tipoTarifa"
            + " AND minutos > 0"
            + " ORDER BY dias desc")
    public abstract List<Tarifas> getAllTarifasMinutoByTipoTarifa(String tipoTarifa);

    @Query("SELECT * FROM Tarifas WHERE TipoTarifa = :tipoTarifa AND Dias = 30 ORDER BY idTarifa DESC LIMIT 1 ")
    public abstract Tarifas getTarifaMesByTipoTarifa(String tipoTarifa);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void add(Tarifas... tarifa);

    @Update
    public abstract void update(Tarifas... tarifa);

    @Delete
    public abstract void delete(Tarifas... tarifas);

}
