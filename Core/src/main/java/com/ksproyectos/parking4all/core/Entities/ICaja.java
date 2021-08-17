/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kevin
 */
public interface ICaja extends Serializable {
    
    Date getFechaApertura();

    Date getFechaCierre();

    Integer getIdCaja();

    Integer getTotal();

    Integer getIdUsuarioApertura();
    
    Integer getIdUsuarioCierre();
    
    Boolean isCerrada();

    void setFechaApertura(Date fechaApertura);

    void setFechaCierre(Date fechaCierre);

    void setIdCaja(Integer idCaja);

    void setTotal(Integer total);
    
    void setIdUsuarioApertura(Integer idUsuario);

    void setIdUsuarioCierre(Integer idUsuario);
    
    void setCerrada(Boolean isCerrada);
}
