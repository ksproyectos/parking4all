/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Business;

import com.ksproyectos.parking4all.core.Services.Abstract.AbstractAuthService;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractPrinterService;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractRepositoryService;

/**
 *
 * @author kevin
 */
public class BusinessServiceProvider {
        
    //servicios
    
    private static AbstractPrinterService printerService;
    
    private static AbstractRepositoryService repositoryService;
    
    private static AbstractAuthService authService;

    
    public BusinessServiceProvider(
            AbstractPrinterService printerService
            , AbstractRepositoryService repositoryService
            , AbstractAuthService authService){
        
        this.printerService = printerService;
        this.repositoryService = repositoryService;
        this.authService = authService;
 
    }

    /**
     * @return the printerService
     */
    public static AbstractPrinterService getPrinterService() {
        return printerService;
    }

    /**
     * @return the repositoryService
     */
    public static AbstractRepositoryService getRepositoryService() {
        return repositoryService;
    }
    
    
    /**
     * @return the authService
     */
    public static AbstractAuthService getAuthService() {
        return authService;
    }
    
    
}
