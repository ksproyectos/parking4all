/*
 * KsProjects 2016.
 * Todos los derechos reservados.
 * Archivo creado por Jhon Kevin Suaza Atehortua.
 */
package com.ksproyectos.parking4all.DAO.SQL.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


/**
 *
 * @author kevin
 */
@Entity(tableName = "Configuraciones")
public class Configuraciones implements Serializable {
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @ColumnInfo(name = "idConfiguracion")
    private Integer idConfiguracion;
    @ColumnInfo(name = "Nombre")
    private String nombre;
    @ColumnInfo(name = "Valor")
    private String valor;

    public Configuraciones() {
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracion != null ? idConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuraciones)) {
            return false;
        }
        Configuraciones other = (Configuraciones) object;
        if ((this.idConfiguracion == null && other.idConfiguracion != null) || (this.idConfiguracion != null && !this.idConfiguracion.equals(other.idConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "parking4all.Configuraciones[ idConfiguracion=" + idConfiguracion + " ]";
    }

}
