/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.Services;

import com.ksproyectos.parking4all.core.Services.Abstract.AbstractAuthService;

/**
 *
 * @author kevin
 */
public class AuthService extends AbstractAuthService {

    private final static AuthService instance = new AuthService();

    public static AuthService getInstance(){
        return instance;
    }

    private AuthService() {

    }

}
