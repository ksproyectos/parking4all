/*
 * KsProjects 2016.
 * Todos los derechos reservados.
 * Archivo creado por Jhon Kevin Suaza Atehortua.
 */
package com.ksproyectos.parking4all.DAO.Mysql.Entities;

import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "Mensualidades")
@XmlRootElement
public class Mensualidades implements Serializable, IMensualidades {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMensualidad")
    private Integer idMensualidad;
    @Column(name = "Placa")
    private String placa;
    @Column(name = "FechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "NumeroDocumento")
    private String numeroDocumento;
    @Column(name = "Nombres")
    private String nombres;
    @Column(name = "Telefono")
    private String telefono;
    @Column(name = "ValorTotal")
    private Integer valorTotal;

    public Mensualidades() {
    }
    
    public Integer getIdMensualidad() {
        return idMensualidad;
    }

    public void setIdMensualidad(Integer idMensualidad) {
        this.idMensualidad = idMensualidad;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensualidad != null ? idMensualidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensualidades)) {
            return false;
        }
        Mensualidades other = (Mensualidades) object;
        if ((this.idMensualidad == null && other.idMensualidad != null) || (this.idMensualidad != null && !this.idMensualidad.equals(other.idMensualidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "parking4all.Mensualidades[ idMensualidad=" + idMensualidad + " ]";
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the valorTotal
     */
    public Integer getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }
    
}
