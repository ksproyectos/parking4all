/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Repository;

import com.ksproyectos.parking4all.core.DAO.DAOException;
import com.ksproyectos.parking4all.core.DAO.IBaseDAO;
import com.ksproyectos.parking4all.core.DAO.ITarifasDAO;
import com.ksproyectos.parking4all.core.DAO.TarifasQueries;
import com.ksproyectos.parking4all.core.Entities.ITarifas;
import java.util.List;

/**
 *
 * @author kevin
 */
public class TarifasRepository extends AbstractBaseRepositor<ITarifas>{
    
    ITarifasDAO dao;
    
    @Override
    public IBaseDAO getDAO() {
        return this.dao;
    }
    
    public TarifasRepository(ITarifasDAO dao){    
        this.dao = dao;
    }
    
    public List<ITarifas> getAllTarifas(){
        return this.dao.read();
    }
    
    public void addTarifa(ITarifas tarifa){
        dao.create(tarifa);
    }
    
    public ITarifas getTarifaMesByTipoTarifa(String tipoTarifa) {
        
        ITarifas tarifa = getEntity();
        tarifa.setTipoTarifa(tipoTarifa);
        
        return dao.find(TarifasQueries.GET_TARIFA_MES_BY_TIPOTARIFA, tarifa); 
    }  
    
    public List<ITarifas> getAllTarifasDiaByTipoTarifa(String tipoTarifa) {
        
        ITarifas tarifa = getEntity();
        tarifa.setTipoTarifa(tipoTarifa);
        
        return dao.findAll(TarifasQueries.GETALL_TARIFAS_DIA_BY_TIPOTARIFA, tarifa);
    }
    public List<ITarifas> getAllTarifasHoraByTipoTarifa(String tipoTarifa) {
        
        ITarifas tarifa = getEntity();
        tarifa.setTipoTarifa(tipoTarifa);
        
        return dao.findAll(TarifasQueries.GETALL_TARIFAS_HORA_BY_TIPOTARIFA, tarifa); 
    }
    public List<ITarifas> getAllTarifasMinutoByTipoTarifa(String tipoTarifa) {
        
        ITarifas tarifa = getEntity();
        tarifa.setTipoTarifa(tipoTarifa);
        
        return dao.findAll(TarifasQueries.GETALL_TARIFAS_MINUTO_BY_TIPOTARIFA, tarifa); 
    } 

    public void removeTarifa(ITarifas tarifa){
        dao.delete(tarifa);
    }

    public List<ITarifas> getAllTarifasPrepagadasActivas(){
        return this.dao.findAll(TarifasQueries.GETALL_TARIFAS_PREPAGADAS);
    }
}
