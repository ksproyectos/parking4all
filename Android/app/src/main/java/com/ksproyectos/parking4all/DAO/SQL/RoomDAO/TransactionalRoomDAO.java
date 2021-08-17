package com.ksproyectos.parking4all.DAO.SQL.RoomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Transaction;

import com.ksproyectos.parking4all.core.DAO.FunctionWithException;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import java.util.function.Function;


@Dao
public abstract class TransactionalRoomDAO {

    @Transaction
    public boolean executeTransaction(Function<Void, Boolean> transaction){
        return transaction.apply(null);
    }

}
