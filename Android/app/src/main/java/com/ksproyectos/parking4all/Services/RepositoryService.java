/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.Services;

import com.ksproyectos.parking4all.DAO.SQL.CajaDAOImpl;
import com.ksproyectos.parking4all.DAO.SQL.CustomQueriesDAOImpl;
import com.ksproyectos.parking4all.DAO.SQL.MensualidadesDAOImpl;
import com.ksproyectos.parking4all.DAO.SQL.MovimientosDAOImpl;
import com.ksproyectos.parking4all.DAO.SQL.TarifasDAOImpl;
import com.ksproyectos.parking4all.DAO.SQL.TransactionalImpl;
import com.ksproyectos.parking4all.DAO.SQL.UsuariosDAOImpl;
import com.ksproyectos.parking4all.core.DAO.ICajaDAO;
import com.ksproyectos.parking4all.core.DAO.ICustomQueriesDAO;
import com.ksproyectos.parking4all.core.DAO.IMensualidadesDAO;
import com.ksproyectos.parking4all.core.DAO.IMovimientosDAO;
import com.ksproyectos.parking4all.core.DAO.ITarifasDAO;
import com.ksproyectos.parking4all.core.DAO.ITransactional;
import com.ksproyectos.parking4all.core.DAO.IUsuariosDAO;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractRepositoryService;

/**
 *
 * @author kevin
 */
public class RepositoryService extends AbstractRepositoryService{

    private final static RepositoryService instance = new RepositoryService();

    public static RepositoryService getInstance(){
        return instance;
    }

    private RepositoryService() {

    }

    @Override
    protected IMovimientosDAO getMovimientosDAO() {
        return new MovimientosDAOImpl();
    }

    @Override
    protected ICajaDAO getCajaDAO() {
        return new CajaDAOImpl();
    }

    @Override
    protected ITarifasDAO getTarifasDAO() { return new TarifasDAOImpl(); }

    @Override
    protected IMensualidadesDAO getMensualidadesDAO() {
        return new MensualidadesDAOImpl();
    }

    @Override
    protected ICustomQueriesDAO getCustomQueriesDAO() {
        return new CustomQueriesDAOImpl();
    }

    @Override
    protected ITransactional getTransactional() {
        return new TransactionalImpl();
    }

    @Override
    protected IUsuariosDAO getUsuariosDAO() {
        return new UsuariosDAOImpl();
    }

}
