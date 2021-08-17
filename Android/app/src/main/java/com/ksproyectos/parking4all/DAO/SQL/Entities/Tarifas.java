/*
 * KsProjects 2016.
 * Todos los derechos reservados.
 * Archivo creado por Jhon Kevin Suaza Atehortua.
 */
package com.ksproyectos.parking4all.DAO.SQL.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.ksproyectos.parking4all.core.Entities.ITarifas;

import java.beans.PropertyChangeListener;
import java.io.Serializable;


/**
 *
 * @author kevin
 */
@Entity(tableName = "Tarifas")
public class Tarifas implements Serializable, ITarifas {

    @PrimaryKey
    @ColumnInfo(name = "idTarifa")
    private Integer idTarifa;
    @ColumnInfo(name = "Nombre")
    private String nombre;
    @ColumnInfo(name = "Tarifa")
    private int tarifa;
    @ColumnInfo(name = "Dias")
    private Integer dias;
    @ColumnInfo(name = "Horas")
    private Integer horas;
    @ColumnInfo(name = "Minutos")
    private Integer minutos;
    @ColumnInfo(name = "TipoTarifa")
    private String tipoTarifa;

    public Tarifas() {
    }

    public Integer getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Integer idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

    public String getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

}
