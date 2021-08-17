/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.DAO;

import com.ksproyectos.parking4all.core.Entities.IMovimientos;

/**
 *
 * @author kevin
 */
public interface IMovimientosDAO  extends IBaseDAO<IMovimientos>,  ICRUD<IMovimientos>, IDAOFindable<IMovimientos, MovimientosQueries>{

}
