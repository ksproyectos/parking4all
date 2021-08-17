/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Entities;

import java.io.Serializable;

/**
 *
 * @author kevin
 */
public interface IConfiguraciones extends Serializable {

    Integer getIdConfiguracion();

    String getNombre();

    String getValor();

    void setIdConfiguracion(Integer idConfiguracion);

    void setNombre(String nombre);

    void setValor(String valor);

    
}
