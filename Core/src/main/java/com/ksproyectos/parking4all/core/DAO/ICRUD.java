/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.DAO;

import java.util.List;

/**
 *
 * @author kevin
 */
public  interface ICRUD<T> {
    
    public void create(T entity);
    public List read();
    public T read(T entity);
    public void delete(T entity);
    public void update(T entity);
    
}
