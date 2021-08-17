/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Repository;

import com.ksproyectos.parking4all.core.DAO.CajaQueries;
import com.ksproyectos.parking4all.core.DAO.IBaseDAO;
import com.ksproyectos.parking4all.core.DAO.ICajaDAO;
import com.ksproyectos.parking4all.core.Entities.ICaja;

/**
 *
 * @author kevin
 */
public class CajaRepository extends AbstractBaseRepositor<ICaja>{
    
    ICajaDAO dao;
    
    public CajaRepository(ICajaDAO dao){
        this.dao = dao;
    }
    
    public ICaja getCajaActiva(){
        return dao.find(CajaQueries.GETCAJAACTIVA,  getEntity());
    }

    public void updateCaja(ICaja caja) {
        dao.update(caja);
    }
    
    public void createCaja(ICaja caja){
        dao.create(caja);
    }

    @Override
    public IBaseDAO getDAO() {
       return this.dao;
    }
}
