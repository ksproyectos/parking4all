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
public interface IUsuarios extends Serializable {

    Boolean getAdministrador();

    Integer getIdUsuario();

    Boolean getInactivo();

    String getNombre();

    String getPassword();

    String getUsername();

    void setAdministrador(Boolean administrador);

    void setIdUsuario(Integer idUsuario);

    void setInactivo(Boolean inactivo);

    void setNombre(String nombre);

    void setPassword(String password);

    void setUsername(String username);
    
}
