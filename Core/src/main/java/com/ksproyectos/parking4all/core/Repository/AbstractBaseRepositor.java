/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Repository;

import com.ksproyectos.parking4all.core.DAO.IBaseDAO;

/**
 *
 * @author kevin
 */
public abstract class AbstractBaseRepositor <T> {

    public abstract IBaseDAO getDAO(); 
    
    public T getEntity(){
        return (T) getDAO().getEntity();
    }
}
