/*
 * KsProjects 2016.
 * Todos los derechos reservados.
 * Archivo creado por Jhon Kevin Suaza Atehortua.
 */
package com.ksproyectos.parking4all.DAO.SQL.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.ksproyectos.parking4all.core.Entities.IMovimientos;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author kevin
 */
@Entity(tableName = "Movimientos", indices = {@Index(value = {"Placa", "FechaSalida"}, unique = true),@Index(value = {"Placa", "FechaEntrada"}, unique = true)})
public class Movimientos implements Serializable, IMovimientos {
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @ColumnInfo(name = "IdMovimiento")
    private Integer idMovimiento;

    @ColumnInfo(name = "IdMovimientoServidor")
    private String idMovimientoServidor;

    @ColumnInfo(name = "Placa", index = true)
    private String placa;
    @ColumnInfo(name = "FechaEntrada")
    @TypeConverters({DateConverter.class})
    private Date fechaEntrada;
    @ColumnInfo(name = "FechaSalida")
    @TypeConverters({DateConverter.class})
    private Date fechaSalida;
    @ColumnInfo(name = "Finalizado", index = true)
    private Boolean finalizado;
    @ColumnInfo(name = "TipoTarifa")
    private String tipoTarifa;
    @ColumnInfo(name = "ValorTotal")
    private Integer valorTotal;
    @ColumnInfo(name = "IdUsuarioEntrada")
    private Integer idUsuarioEntrada;
    @ColumnInfo(name = "IdUsuarioSalida")
    private Integer idUsuarioSalida;

    public Movimientos() {
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public String getTipoTarifa() {
        return tipoTarifa;
    }

    @Override
    public Integer getIdUsuarioEntrada() {
        return this.idUsuarioEntrada;
    }

    @Override
    public Integer getIdUsuarioSalida() {
        return this.idUsuarioSalida;
    }

    public Integer getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setTipoTarifa(String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    @Override
    public void setIdUsuarioEntrada(Integer IdUsuarioEntrada) {

        this.idUsuarioEntrada = IdUsuarioEntrada;
    }

    @Override
    public void setIdUsuarioSalida(Integer IdUsuarioSalida) {

        this.idUsuarioSalida = IdUsuarioSalida;
    }

    public void setIdMovimientoServidor(String idMovimientoServidor){
        this.idMovimientoServidor = idMovimientoServidor;
    }

    public String getIdMovimientoServidor(){
        return this.idMovimientoServidor;
    }
}