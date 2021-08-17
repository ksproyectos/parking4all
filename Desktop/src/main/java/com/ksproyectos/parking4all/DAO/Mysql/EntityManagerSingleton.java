/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksproyectos.parking4all.DAO.Mysql;

import javax.persistence.EntityManager;

/**
 *
 * @author kevin
 */
public class EntityManagerSingleton {
    
    private static final EntityManagerSingleton instance = new EntityManagerSingleton();
    private final EntityManager entityManager;
    
    private EntityManagerSingleton(){
        entityManager = javax.persistence.Persistence.createEntityManagerFactory("Parking4All?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
    }

    public static EntityManagerSingleton getInstance(){
        return instance;
    }
    
    public EntityManager getEntityManager(){
        return this.entityManager;
    }    
}
