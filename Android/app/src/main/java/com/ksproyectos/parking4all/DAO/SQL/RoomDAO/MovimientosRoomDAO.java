package com.ksproyectos.parking4all.DAO.SQL.RoomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;

import java.util.Date;
import java.util.List;

@Dao
public abstract class MovimientosRoomDAO {

    @Query("SELECT * FROM movimientos m WHERE m.Placa like :placa AND m.FechaSalida IS NULL")
    public abstract List<Movimientos> getAllMovimientosByPlaca(String placa);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long add(Movimientos movimientos);

    @Update
    public abstract void update(Movimientos... movimientos);

    @Query("SELECT * from movimientos where idMovimiento = :id LIMIT 1")
    public abstract Movimientos getById(int id);

    @Query("SELECT * from movimientos where idMovimientoServidor = :idMovimientoServidor")
    public abstract Cursor getByIdMovimientoServidor(int idMovimientoServidor);

    @Query("SELECT * FROM movimientos m WHERE m.FechaEntrada BETWEEN :fechaDesde AND :fechaHasta AND m.TipoTarifa IN (:tiposTarifa) ")
    public abstract List<Movimientos> getAllMovimientosEntradaByFechaDesdeHasta(Date fechaDesde, Date fechaHasta, List<String> tiposTarifa);

    @Query("SELECT * FROM movimientos m WHERE m.FechaEntrada BETWEEN :fechaDesde AND :fechaHasta")
    public abstract List<Movimientos> getAllMovimientosEntradaByFechaDesdeHasta(Date fechaDesde, Date fechaHasta);

    @Query("SELECT * FROM movimientos m WHERE m.FechaSalida BETWEEN :fechaDesde AND :fechaHasta AND m.TipoTarifa IN (:tiposTarifa)")
    public abstract List<Movimientos> getAllMovimientosSalidaByFechaDesdeHasta(Date fechaDesde, Date fechaHasta, List<String> tiposTarifa);

    @Query("SELECT * FROM movimientos m WHERE m.FechaSalida BETWEEN :fechaDesde AND :fechaHasta")
    public abstract List<Movimientos> getAllMovimientosSalidaByFechaDesdeHasta(Date fechaDesde, Date fechaHasta);


    //Para sincronizacion con servidor
    @Query("SELECT * FROM movimientos m WHERE m.FechaEntrada BETWEEN :fechaDesde AND :fechaHasta ")
    public abstract Cursor getAllMovimientosEntradaSalidaByFechaDesdeHasta(Date fechaDesde, Date fechaHasta);
}
