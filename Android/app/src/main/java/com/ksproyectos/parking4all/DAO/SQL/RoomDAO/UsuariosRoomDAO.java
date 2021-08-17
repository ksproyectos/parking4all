package com.ksproyectos.parking4all.DAO.SQL.RoomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Usuarios;

import java.util.List;

@Dao
public abstract class UsuariosRoomDAO {

    @Query("SELECT * From Usuarios WHERE inactivo <> 1")
    public abstract List<Usuarios> getAllUsuarios();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long add(Usuarios usuarios);

    @Query("SELECT * From Usuarios WHERE idUsuario = :idUsuario")
    public abstract  Usuarios getUsuarioById(int idUsuario);

    @Query("UPDATE usuarios SET inactivo = 1 WHERE idUsuario = :idUsuario")
    public abstract void desactivarUsuario(int idUsuario);

    @Query("SELECT * From Usuarios WHERE username = :username and password = :password and inactivo <> 1")
    public abstract Usuarios getUsuarioByUsernameAndPassword(String username, String password);

}
