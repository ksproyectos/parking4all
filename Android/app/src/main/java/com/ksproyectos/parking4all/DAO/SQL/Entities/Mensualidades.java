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

import com.ksproyectos.parking4all.core.Entities.IMensualidades;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author kevin
 */
@Entity(tableName = "Mensualidades")
public class Mensualidades implements Serializable, IMensualidades {
    private static final long serialVersionUID = 1L;
    @PrimaryKey
    @ColumnInfo(name = "idMensualidad")
    private Integer idMensualidad;
    @ColumnInfo(name = "Placa")
    private String placa;
    @ColumnInfo(name = "FechaPago")
    @TypeConverters({DateConverter.class})
    private Date fechaPago;
    @ColumnInfo(name = "NumeroDocumento")
    private String numeroDocumento;
    @ColumnInfo(name = "Nombres")
    private String nombres;
    @ColumnInfo(name = "Telefono")
    private String telefono;
    @ColumnInfo(name = "ValorTotal")
    private Integer valorTotal;

    public Mensualidades() {
    }
    @Override
    public Integer getIdMensualidad() {
        return idMensualidad;
    }
    @Override
    public void setIdMensualidad(Integer idMensualidad) {
        this.idMensualidad = idMensualidad;
    }
    @Override
    public String getPlaca() {
        return placa;
    }
    @Override
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    @Override
    public Date getFechaPago() {
        return fechaPago;
    }
    @Override
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    @Override
    public String getNumeroDocumento() {
        return numeroDocumento;
    }
    @Override
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    @Override
    public String getNombres() {
        return nombres;
    }

    @Override
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public Integer getValorTotal() {
        return valorTotal;
    }
    @Override
    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }
}
