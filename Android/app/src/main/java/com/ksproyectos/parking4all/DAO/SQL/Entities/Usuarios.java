package com.ksproyectos.parking4all.DAO.SQL.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.ksproyectos.parking4all.core.Entities.IUsuarios;

@Entity(tableName = "Usuarios")
public class Usuarios implements IUsuarios {
    @PrimaryKey
    @ColumnInfo(name = "idUsuario")
    private Integer idUsuario;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "inactivo")
    private Boolean inactivo;

    @ColumnInfo(name = "administrador")
    private Boolean administrador;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getInactivo() {
        return inactivo;
    }

    public void setInactivo(Boolean inactivo) {
        this.inactivo = inactivo;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }
}
