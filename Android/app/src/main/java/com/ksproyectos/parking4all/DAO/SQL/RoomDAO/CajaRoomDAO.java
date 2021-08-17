package com.ksproyectos.parking4all.DAO.SQL.RoomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Caja;


@Dao
public abstract class CajaRoomDAO {

    @Query("SELECT * From Caja WHERE fechaCierre IS NULL")
    public abstract Caja getCajaActiva();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addReplace(Caja... caja);

    @Query("SELECT * FROM Caja WHERE idCaja = :idCaja")
    public abstract Caja getById(Integer idCaja);

}
