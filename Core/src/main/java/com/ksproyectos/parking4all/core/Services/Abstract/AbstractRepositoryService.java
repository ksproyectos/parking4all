/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Services.Abstract;

import com.ksproyectos.parking4all.core.DAO.FunctionWithException;
import com.ksproyectos.parking4all.core.DAO.ICajaDAO;
import com.ksproyectos.parking4all.core.DAO.ICustomQueriesDAO;
import com.ksproyectos.parking4all.core.DAO.IMensualidadesDAO;
import com.ksproyectos.parking4all.core.DAO.IMovimientosDAO;
import com.ksproyectos.parking4all.core.DAO.ITarifasDAO;
import com.ksproyectos.parking4all.core.DAO.ITransactional;
import com.ksproyectos.parking4all.core.DAO.IUsuariosDAO;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Repository.CajaRepository;
import com.ksproyectos.parking4all.core.Repository.CustomQueriesRepository;
import com.ksproyectos.parking4all.core.Repository.MensualidadesRepository;
import com.ksproyectos.parking4all.core.Repository.MovimientosRepository;
import com.ksproyectos.parking4all.core.Repository.TarifasRepository;
import com.ksproyectos.parking4all.core.Repository.UsuariosRepository;
import java.util.function.Function;

/**
 *
 * @author kevin
 */
public abstract class AbstractRepositoryService {
    
    private MovimientosRepository movimientosRepository = new MovimientosRepository(getMovimientosDAO());
    
    private CajaRepository cajaRepository = new CajaRepository(getCajaDAO());
    
    private TarifasRepository tarifasRepository = new TarifasRepository(getTarifasDAO());
    
    private MensualidadesRepository mensualidadesRepository = new MensualidadesRepository(getMensualidadesDAO());
       
    private CustomQueriesRepository customQueriesRepository = new CustomQueriesRepository(getCustomQueriesDAO());
    
    private ITransactional transactional = getTransactional();
    
    private UsuariosRepository usuariosRepository = new UsuariosRepository(getUsuariosDAO());
            
    protected abstract IMovimientosDAO getMovimientosDAO();
    protected abstract ICajaDAO getCajaDAO();
    protected abstract ITarifasDAO getTarifasDAO();
    protected abstract IMensualidadesDAO getMensualidadesDAO();
    protected abstract ICustomQueriesDAO getCustomQueriesDAO();
    protected abstract ITransactional getTransactional();
    protected abstract IUsuariosDAO getUsuariosDAO();
    
    
   
    public boolean executeTransaction(FunctionWithException<Void, Boolean> onProccess) throws Parking4AllException{
        return transactional.executeTransaction(onProccess);
    }

    /**
     * @param movimientosRepository the movimientosRepository to set
     */
    public void setMovimientosRepository(MovimientosRepository movimientosRepository) {
        this.movimientosRepository = movimientosRepository;
    }

    /**
     * @param cajaRepository the cajaRepository to set
     */
    public void setCajaRepository(CajaRepository cajaRepository) {
        this.cajaRepository = cajaRepository;
    }

    /**
     * @param tarifasRepository the tarifasRepository to set
     */
    public void setTarifasRepository(TarifasRepository tarifasRepository) {
        this.tarifasRepository = tarifasRepository;
    }

    /**
     * @param mensualidadesRepository the mensualidadesRepository to set
     */
    public void setMensualidadesRepository(MensualidadesRepository mensualidadesRepository) {
        this.mensualidadesRepository = mensualidadesRepository;
    }

    /**
     * @param customQueriesRepository the customQueriesRepository to set
     */
    public void setCustomQueriesRepository(CustomQueriesRepository customQueriesRepository) {
        this.customQueriesRepository = customQueriesRepository;
    }

    /**
     * @param usuariosRepository the usuariosRepository to set
     */
    public void setUsuariosRepository(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    /**
     * @return the movimientosRepository
     */
    public MovimientosRepository getMovimientosRepository() {
        return movimientosRepository;
    }

    /**
     * @return the cajaRepository
     */
    public CajaRepository getCajaRepository() {
        return cajaRepository;
    }

    /**
     * @return the tarifasRepository
     */
    public TarifasRepository getTarifasRepository() {
        return tarifasRepository;
    }

    /**
     * @return the mensualidadesRepository
     */
    public MensualidadesRepository getMensualidadesRepository() {
        return mensualidadesRepository;
    }

    /**
     * @return the customQueriesRepository
     */
    public CustomQueriesRepository getCustomQueriesRepository() {
        return customQueriesRepository;
    }
    
    /**
     * @return the usuariosRepository
     */
    public UsuariosRepository getUsuariosRepository() {
        return usuariosRepository;
    }
    
}
