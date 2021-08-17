/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.DAO.SQL;

import com.ksproyectos.parking4all.core.DAO.FunctionWithException;
import com.ksproyectos.parking4all.core.DAO.ITransactional;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

/**
 *
 * @author kevin
 */
public class TransactionalImpl implements ITransactional{

    Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase();

    @Override
    public boolean executeTransaction(FunctionWithException<Void, Boolean> transaction) throws Parking4AllException {
        db.beginTransaction();
        try {
            boolean _result = transaction.apply(null);
            db.setTransactionSuccessful();
            return _result;
        } catch(Exception e){
            throw new Parking4AllException("Error al ejecutar la transacción.", e);
        }finally {
            if(db.isOpen()){
                db.endTransaction();
            }
        }
    }

}
