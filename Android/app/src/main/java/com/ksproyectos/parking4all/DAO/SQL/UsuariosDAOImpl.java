/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.SQL;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Usuarios;
import com.ksproyectos.parking4all.core.DAO.IUsuariosDAO;
import com.ksproyectos.parking4all.core.Entities.IUsuarios;

import java.util.List;

/**
 *
 * @author kevin
 */
public class UsuariosDAOImpl implements IUsuariosDAO {

    Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase();


    @Override
    public IUsuarios getEntity() {
        return new Usuarios();
    }

    @Override
    public void create(IUsuarios entity) {

        if(entity.getAdministrador()){
            db.usuariosRoomDAO().add((Usuarios) entity);
        }
    }

    @Override
    public List read() {
       return db.usuariosRoomDAO().getAllUsuarios();
    }

    @Override
    public IUsuarios read(IUsuarios entity) {
        return db.usuariosRoomDAO().getUsuarioById(entity.getIdUsuario());
    }

    @Override
    public void delete(IUsuarios entity) {

    }

    @Override
    public void update(IUsuarios entity) {

    }
}
