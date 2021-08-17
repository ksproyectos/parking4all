/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.DAO;

/**
 *
 * @author kevin
 */
public class DAOException extends Exception{
    
    public DAOException(String mensaje, Exception e){
        super(mensaje, e);
    }
    
}
