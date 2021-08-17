/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Repository;

import com.ksproyectos.parking4all.core.DAO.IBaseDAO;
import com.ksproyectos.parking4all.core.DAO.IUsuariosDAO;
import com.ksproyectos.parking4all.core.Entities.IUsuarios;
import java.util.List;

/**
 *
 * @author kevin
 */
public class UsuariosRepository extends AbstractBaseRepositor<IUsuarios>{
    
    IUsuariosDAO dao;
    
    @Override
    public IBaseDAO getDAO() {
        return this.dao;
    }
    
    public UsuariosRepository(IUsuariosDAO dao){    
        this.dao = dao;
    }
    
    public void addUsuario(IUsuarios usuario){
        this.dao.create(usuario);
    }
    
    public void saveUsuario(IUsuarios usuario){
        this.dao.update(usuario);
    }
    
    public void removeUsuario(IUsuarios usuario){
        this.dao.delete(usuario);
    }
    
    public List<IUsuarios> getAllUsuarios(){
        return this.dao.read();
    }
    
}
