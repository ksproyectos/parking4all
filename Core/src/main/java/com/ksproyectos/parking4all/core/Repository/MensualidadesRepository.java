/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Repository;

import com.ksproyectos.parking4all.core.DAO.DAOException;
import com.ksproyectos.parking4all.core.DAO.IBaseDAO;
import com.ksproyectos.parking4all.core.DAO.IMensualidadesDAO;
import com.ksproyectos.parking4all.core.DAO.MensualidadesQueries;
import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kevin
 */ 
public class MensualidadesRepository extends AbstractBaseRepositor<IMensualidades>{
    
    IMensualidadesDAO dao;
    
    public MensualidadesRepository(IMensualidadesDAO dao){    
        this.dao = dao;
    }
  
    public IMensualidades getMensualidadActivaByPlaca(String placa){
        
        IMensualidades mensualidad = getEntity();
        
        mensualidad.setPlaca(placa);
        
        return dao.find(MensualidadesQueries.GET_MENSUALIDAD_BY_PLACA, mensualidad);
    }
    
    public List<IMensualidades> getMensualidadesBy(Date fechaPagoDesde, Date fechaPagoHasta){    
        return dao.findAll(
                MensualidadesQueries.GET_MENSUALIDAD_BY_FECHAPAGODESDE_FECHAPAGOHASTA
                , fechaPagoDesde
                , fechaPagoHasta );
    }
    
    public void removeMensualidad(IMensualidades mensualidad){
        dao.delete(mensualidad);
    }
    
    public void addMensualidad(IMensualidades mensualidad){
        dao.create(mensualidad);
    }

    @Override
    public IBaseDAO getDAO() {
        return this.dao;
    }
  
}
