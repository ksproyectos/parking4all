/*
 * KsProjects 2016.
 * Todos los derechos reservados.
 * Archivo creado por Jhon Kevin Suaza Atehortua.
 */
package com.ksproyectos.parking4all.DAO.Mysql.Entities;

import com.ksproyectos.parking4all.core.Entities.ICaja;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "Caja")
@XmlRootElement
public class Caja implements Serializable, ICaja {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCaja")
    private Integer idCaja;
    @Column(name = "FechaCierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Basic(optional = false)
    @Column(name = "FechaApertura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;
    @Column(name = "Total")
    private Integer total;
    
    @Column(name = "Cerrada")
    private Boolean cerrada;
    
    @JoinColumn(name = "IdUsuarioCierre", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuarios usuariosidUsuarioCierre;

    @JoinColumn(name = "IdUsuarioApertura", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuarios usuariosidUsuarioApertura;
    
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

    public void setTotal(Integer total) {
        this.total = total;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaja != null ? idCaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caja)) {
            return false;
        }
        Caja other = (Caja) object;
        if ((this.idCaja == null && other.idCaja != null) || (this.idCaja != null && !this.idCaja.equals(other.idCaja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "parking4all.Caja[ idCaja=" + idCaja + " ]";
    }

    @Override
    public Integer getIdUsuarioApertura() {
        return this.getUsuariosidUsuarioApertura().getIdUsuario();
    }

    @Override
    public Integer getIdUsuarioCierre() {
        return this.getUsuariosidUsuarioCierre().getIdUsuario();
    }

    @Override
    public Boolean isCerrada() {
        return this.cerrada;
    }

    @Override
    public void setIdUsuarioApertura(Integer idUsuario) {
        Usuarios usuario = new Usuarios();
        usuario.setIdUsuario(idUsuario);
        setUsuariosidUsuarioApertura(usuario);
        
    }

    @Override
    public void setIdUsuarioCierre(Integer idUsuario) {
        Usuarios usuario = new Usuarios();
        usuario.setIdUsuario(idUsuario);
        setUsuariosidUsuarioCierre(usuario);
    }

    @Override
    public void setCerrada(Boolean isCerrada) {
        this.cerrada = isCerrada;
    }

    /**
     * @return the usuariosidUsuarioCierre
     */
    public Usuarios getUsuariosidUsuarioCierre() {
        return usuariosidUsuarioCierre;
    }

    /**
     * @param usuariosidUsuarioCierre the usuariosidUsuarioCierre to set
     */
    public void setUsuariosidUsuarioCierre(Usuarios usuariosidUsuarioCierre) {
        this.usuariosidUsuarioCierre = usuariosidUsuarioCierre;
    }

    /**
     * @return the usuariosidUsuarioApertura
     */
    public Usuarios getUsuariosidUsuarioApertura() {
        return usuariosidUsuarioApertura;
    }

    /**
     * @param usuariosidUsuarioApertura the usuariosidUsuarioApertura to set
     */
    public void setUsuariosidUsuarioApertura(Usuarios usuariosidUsuarioApertura) {
        this.usuariosidUsuarioApertura = usuariosidUsuarioApertura;
    }
    
}
