/*
 * KsProjects 2016.
 * Todos los derechos reservados.
 * Archivo creado por Jhon Kevin Suaza Atehortua.
 */
package com.ksproyectos.parking4all.DAO.Mysql.Entities;

import com.ksproyectos.parking4all.core.Entities.IMovimientos;
import com.ksproyectos.parking4all.core.Entities.IUsuarios;
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
@Table(name = "Movimientos")
@XmlRootElement
public class Movimientos implements Serializable, IMovimientos {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMovimiento")
    private Integer idMovimiento;
    @Basic(optional = false)
    @Column(name = "Placa")
    private String placa;
    @Basic(optional = false)
    @Column(name = "FechaEntrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrada;
    @Column(name = "FechaSalida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @Column(name = "Finalizado")
    private Boolean finalizado;
    @Basic(optional = false)
    @Column(name = "TipoTarifa")
    private String tipoTarifa;
    @Column(name = "ValorTotal")
    private Integer valorTotal;
    
    
    
    
    @JoinColumn(name = "Usuarios_idUsuarioEntrada", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuarios usuariosidUsuarioEntrada;
    @JoinColumn(name = "Usuarios_idUsuarioSalida", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuarios usuariosidUsuarioSalida;

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

    public void setTipoTarifa(String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public Integer getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Usuarios getUsuariosidUsuarioEntrada() {
        return usuariosidUsuarioEntrada;
    }

    public void setUsuariosidUsuarioEntrada(IUsuarios usuariosidUsuarioEntrada) {
        this.usuariosidUsuarioEntrada = (Usuarios) usuariosidUsuarioEntrada;
    }

    public Usuarios getUsuariosidUsuarioSalida() {
        return usuariosidUsuarioSalida;
    }

    public void setUsuariosidUsuarioSalida(IUsuarios usuariosidUsuarioSalida) {
        this.usuariosidUsuarioSalida = (Usuarios) usuariosidUsuarioSalida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimiento != null ? idMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientos)) {
            return false;
        }
        Movimientos other = (Movimientos) object;
        if ((this.idMovimiento == null && other.idMovimiento != null) || (this.idMovimiento != null && !this.idMovimiento.equals(other.idMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "parking4all.Movimientos[ idMovimiento=" + idMovimiento + " ]";
    }

    @Override
    public Integer getIdUsuarioEntrada() {
        return getUsuariosidUsuarioEntrada().getIdUsuario();
    }

    @Override
    public Integer getIdUsuarioSalida() {
        return getUsuariosidUsuarioSalida().getIdUsuario();
    }

    @Override
    public void setIdUsuarioEntrada(Integer IdUsuarioEntrada) {
        Usuarios usuario = new Usuarios();
        usuario.setIdUsuario(IdUsuarioEntrada);
        setUsuariosidUsuarioEntrada(usuario);
    }

    @Override
    public void setIdUsuarioSalida(Integer IdUsuarioSalida) {
        Usuarios usuario = new Usuarios();
        usuario.setIdUsuario(IdUsuarioSalida);
        setUsuariosidUsuarioSalida(usuario);
    }




}
