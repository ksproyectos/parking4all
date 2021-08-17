/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Business;

import com.ksproyectos.parking4all.core.Entities.IUsuarios;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Services.Abstract.AbstractAuthService;
import java.util.List;

/**
 *
 * @author kevin
 */
public class UsuariosBusiness {

    private final BusinessServiceProvider businessServiceProvider;
    
    public UsuariosBusiness(BusinessServiceProvider businessServiceProvider) {
        this.businessServiceProvider = businessServiceProvider;
    }
    
    public void crearUsuario(IUsuarios usuario) throws Parking4AllException{
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.CREATE_USUARIOS)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        this.businessServiceProvider
                .getRepositoryService()
                .getUsuariosRepository()
                .addUsuario(usuario);
    }
    
    public void eliminarUsuario(IUsuarios usuario) throws Parking4AllException{
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.DELETE_MENSUALIDADES)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        this.businessServiceProvider
                .getRepositoryService()
                .getUsuariosRepository()
                .removeUsuario(usuario);
    }
    
    public void actualizarUsuario(IUsuarios usuario){
        
        this.businessServiceProvider
                .getRepositoryService()
                .getUsuariosRepository()
                .saveUsuario(usuario);
    }

    public List<IUsuarios> obtenerUsuarios() throws Parking4AllException{
        if(!businessServiceProvider.getAuthService().allows(AbstractAuthService.Permisos.LIST_USUARIOS)){
            throw new Parking4AllException("No tiene permisos para realizar esta accion");
        }
        return this.businessServiceProvider
                .getRepositoryService()
                .getUsuariosRepository()
                .getAllUsuarios();
    }
}
