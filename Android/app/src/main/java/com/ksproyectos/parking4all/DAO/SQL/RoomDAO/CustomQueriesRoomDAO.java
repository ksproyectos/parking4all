package com.ksproyectos.parking4all.DAO.SQL.RoomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Caja;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Configuraciones;

import java.util.Date;
import java.util.List;


@Dao
public abstract class CustomQueriesRoomDAO {

    @Query("SELECT DISTINCT(t.tipoTarifa) FROM Tarifas t")
    public abstract List<String> getAllTiposTarifa();

    @Query("SELECT c.Valor FROM Configuraciones c WHERE c.idConfiguracion = 1")
    public abstract String getLicencia();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void add(Configuraciones... configuracion);
}
