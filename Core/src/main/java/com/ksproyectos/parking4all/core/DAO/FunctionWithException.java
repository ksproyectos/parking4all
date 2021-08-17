/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksproyectos.parking4all.core.DAO;

import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

/**
 *
 * @author john.suaza
 */
@FunctionalInterface
public interface FunctionWithException<T extends Object, R extends Object>{
    public R apply(T t) throws Parking4AllException;
}