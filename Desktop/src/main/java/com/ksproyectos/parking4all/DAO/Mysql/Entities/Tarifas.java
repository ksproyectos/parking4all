/*
 * KsProjects 2016.
 * Todos los derechos reservados.
 * Archivo creado por Jhon Kevin Suaza Atehortua.
 */
package com.ksproyectos.parking4all.DAO.Mysql.Entities;

import com.ksproyectos.parking4all.core.Entities.ITarifas;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.text.NumberFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "Tarifas")
@XmlRootElement
public class Tarifas implements Serializable, ITarifas {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTarifa")
    private Integer idTarifa;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Tarifa")
    private int tarifa;
    @Column(name = "Dias")
    private Integer dias;
    @Column(name = "Horas")
    private Integer horas;
    @Column(name = "Minutos")
    private Integer minutos;
    @Basic(optional = false)
    @Column(name = "TipoTarifa")
    private String tipoTarifa;

    @Column(name = "activa")
    private Boolean activa;

    @Column(name = "prepagada")
    private Boolean prepagada;

    public Tarifas() {
    }

    public Tarifas(Integer idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Tarifas(Integer idTarifa, String nombre, int tarifa, String tipoTarifa) {
        this.idTarifa = idTarifa;
        this.nombre = nombre;
        this.tarifa = tarifa;
        this.tipoTarifa = tipoTarifa;
    }

    public Integer getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Integer idTarifa) {
        Integer oldIdTarifa = this.idTarifa;
        this.idTarifa = idTarifa;
        changeSupport.firePropertyChange("idTarifa", oldIdTarifa, idTarifa);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        String oldNombre = this.nombre;
        this.nombre = nombre;
        changeSupport.firePropertyChange("nombre", oldNombre, nombre);
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        int oldTarifa = this.tarifa;
        this.tarifa = tarifa;
        changeSupport.firePropertyChange("tarifa", oldTarifa, tarifa);
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        Integer oldDias = this.dias;
        this.dias = dias;
        changeSupport.firePropertyChange("dias", oldDias, dias);
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        Integer oldHoras = this.horas;
        this.horas = horas;
        changeSupport.firePropertyChange("horas", oldHoras, horas);
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        Integer oldMinutos = this.minutos;
        this.minutos = minutos;
        changeSupport.firePropertyChange("minutos", oldMinutos, minutos);
    }

    public String getTipoTarifa() {
        return tipoTarifa;
    }

    @Override
    public Boolean isPrepagada() {
        return prepagada;
    }


    @Override
    public Boolean isActiva() {
        return activa;
    }

    public void setTipoTarifa(String tipoTarifa) {
        String oldTipoTarifa = this.tipoTarifa;
        this.tipoTarifa = tipoTarifa;
        changeSupport.firePropertyChange("tipoTarifa", oldTipoTarifa, tipoTarifa);
    }

    @Override
    public void setPrepagada(Boolean prepagada) {
        this.prepagada = prepagada;
    }

    @Override
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarifa != null ? idTarifa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarifas)) {
            return false;
        }
        Tarifas other = (Tarifas) object;
        if ((this.idTarifa == null && other.idTarifa != null) || (this.idTarifa != null && !this.idTarifa.equals(other.idTarifa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return this.nombre + " " + this.tipoTarifa  + " " + formatter.format(this.tarifa);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
