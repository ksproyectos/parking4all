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
public interface IDAOFindable<T, R> {
    public T find(R by, T entity);
    public List<T> findAll(R by, T entity);
    public List<T> findAll(R by, Object... filters);
}
