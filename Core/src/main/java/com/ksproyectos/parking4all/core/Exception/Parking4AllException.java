package com.ksproyectos.parking4all.core.Exception;

/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */

/**
 *
 * @author kevin
 */
public class Parking4AllException extends Exception{
    
    public Parking4AllException(String mensaje, Exception e){
        super(mensaje, e);
    }
    
    public Parking4AllException(String mensaje){
        super(mensaje);
    }
    
}
