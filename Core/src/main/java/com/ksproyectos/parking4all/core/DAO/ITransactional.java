/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.DAO;

import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.function.Function;

/**
 *
 * @author kevin
 */
public interface ITransactional {
    
    public boolean executeTransaction( FunctionWithException<Void, Boolean> transaction ) throws Parking4AllException;
    
}
