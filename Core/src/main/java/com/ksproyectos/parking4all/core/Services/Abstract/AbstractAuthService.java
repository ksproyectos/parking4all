/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Services.Abstract;

import com.ksproyectos.parking4all.core.Entities.IUsuarios;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.util.HashMap;

/**
 *
 * @author kevin
 */
public abstract class AbstractAuthService {
    
    IUsuarios usuario;
    
    public int getIdUsuarioAutenticado(){
        return usuario.getIdUsuario();
    }
    
    public enum Permisos{
        
        LIST_MENSUALIDADES,
        CREATE_MENSUALIDADES,
        DELETE_MENSUALIDADES,
        
        LIST_TARIFAS,
        CREATE_TARIFAS,
        DELETE_TARIFAS,
        
        LIST_MOVIMIENTOS,
        CREATE_MOVIMIENTOS,
        DELETE_MOVIMIENTOS,
        
        LIST_USUARIOS,
        CREATE_USUARIOS,
        DELETE_USUARIOS,
                
        CLOSE_CAJA
        
    }
    
    public void setUsuario(IUsuarios usuario){
        this.usuario = usuario;
    }
    
    
    public boolean allows(Permisos permiso){
        switch(permiso){
            case DELETE_MENSUALIDADES:
            case LIST_TARIFAS:
            case CREATE_TARIFAS:
            case DELETE_TARIFAS:
            case LIST_MOVIMIENTOS:
            case CREATE_MOVIMIENTOS:
            case DELETE_MOVIMIENTOS:
            case LIST_USUARIOS:
            case CREATE_USUARIOS:
            case DELETE_USUARIOS:      
                return usuario.getAdministrador();
        }
        return false;
    }
}
