/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Entities;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

/**
 *
 * @author kevin
 */
public interface ITarifas extends Serializable {

    Integer getDias();

    Integer getHoras();

    Integer getIdTarifa();

    Integer getMinutos();

    String getNombre();

    int getTarifa();

    String getTipoTarifa();

    Boolean isPrepagada();

    Boolean isActiva();

    void setDias(Integer dias);

    void setHoras(Integer horas);

    void setIdTarifa(Integer idTarifa);

    void setMinutos(Integer minutos);

    void setNombre(String nombre);

    void setTarifa(int tarifa);

    void setTipoTarifa(String tipoTarifa);

    void setPrepagada(Boolean isPrepagada);

    void setActiva(Boolean isActiva);
    
}
