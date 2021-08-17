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
public interface IMensualidades extends Serializable {

    Date getFechaPago();

    Integer getIdMensualidad();

    String getNombres();

    String getNumeroDocumento();

    String getPlaca();
    
    String getTelefono();
    
    Integer getValorTotal();

    
    void setFechaPago(Date fechaPago);

    void setIdMensualidad(Integer idMensualidad);

    void setNombres(String nombres);

    void setNumeroDocumento(String numeroDocumento);

    void setPlaca(String placa);
    
    void setTelefono(String telefono);
    
    void setValorTotal(Integer valorTotal);
    
}
