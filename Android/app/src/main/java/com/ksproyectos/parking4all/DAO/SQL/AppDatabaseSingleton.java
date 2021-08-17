package com.ksproyectos.parking4all.DAO.SQL;

import android.arch.persistence.room.Room;
import android.content.Context;

public class AppDatabaseSingleton {

    private Parking4AllDatabase db;

    private static AppDatabaseSingleton instance = new AppDatabaseSingleton();

    private AppDatabaseSingleton(){

    }

    public static synchronized AppDatabaseSingleton getInstance(){
        return instance;
    }

    public Parking4AllDatabase getAppDatabase(Context context){

        if(null == db ) {
            db = Room.databaseBuilder(context,
                    Parking4AllDatabase.class, "Parking4All")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }

        return this.db;
    }


    public Parking4AllDatabase getAppDatabase(){
        return this.db;
    }

}
