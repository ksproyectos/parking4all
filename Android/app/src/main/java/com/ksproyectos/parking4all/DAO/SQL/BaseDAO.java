package com.ksproyectos.parking4all.DAO.SQL;

import java.util.ArrayList;
import java.util.List;

class BaseDAO {

    public static <T> List<T> toInterface(List list){

        List<T> result = new ArrayList<T>();

        for(Object model : list){
            result.add((T) model);
        }

        return result;
    }

}
