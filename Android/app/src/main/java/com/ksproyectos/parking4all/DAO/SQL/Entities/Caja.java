/*
 * KsProjects 2016.
 * Todos los derechos reservados.
 * Archivo creado por Jhon Kevin Suaza Atehortua.
 */
package com.ksproyectos.parking4all.DAO.SQL.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.ksproyectos.parking4all.core.Entities.ICaja;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kevin
 */

@Entity(tableName = "Caja")
public class Caja implements Serializable, ICaja {
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @ColumnInfo(name = "idCaja")
    private Integer idCaja;
    @ColumnInfo(name = "FechaCierre")
    @TypeConverters({DateConverter.class})
    private Date fechaCierre;
    @ColumnInfo(name = "IdUsuarioCierre")
    private Integer idUsuarioCierre;
    @ColumnInfo(name = "FechaApertura")
    @TypeConverters({DateConverter.class})
    private Date fechaApertura;
    @ColumnInfo(name = "IdUsuarioApertura")
    private Integer idUsuarioApertura;
    @ColumnInfo(name = "Total")
    private Integer total;
    @ColumnInfo(name = "Cerrada")
    private Boolean cerrada;

    public Caja() {
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Integer getTotal() {
        return total;
    }

    @Override
    public Integer getIdUsuarioApertura() {
        return this.idUsuarioApertura;
    }

    @Override
    public Integer getIdUsuarioCierre() {
        return this.idUsuarioCierre;
    }

    @Override
    public Boolean isCerrada() {
        return this.cerrada;
    }

    public void setCerrada(Boolean cerrada) {
        this.cerrada = cerrada;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public void setIdUsuarioApertura(Integer idUsuario) {

        this.idUsuarioApertura = idUsuario;
    }

    @Override
    public void setIdUsuarioCierre(Integer idUsuario) {

        this.idUsuarioCierre = idUsuario;
    }

}
