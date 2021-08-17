/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kevin
 */
public interface IMovimientos extends Serializable {

    Date getFechaEntrada();

    Date getFechaSalida();

    Boolean getFinalizado();

    Integer getIdMovimiento();

    String getPlaca();

    String getTipoTarifa();

    Integer getIdUsuarioEntrada();

    Integer getIdUsuarioSalida();

    Integer getValorTotal();

    void setFechaEntrada(Date fechaEntrada);

    void setFechaSalida(Date fechaSalida);

    void setFinalizado(Boolean finalizado);

    void setIdMovimiento(Integer idMovimiento);

    void setPlaca(String placa);

    void setTipoTarifa(String tipoTarifa);

    void setIdUsuarioEntrada(Integer IdUsuarioEntrada);

    void setIdUsuarioSalida(Integer IdUsuarioSalida);

    void setValorTotal(Integer valorTotal);
    
}
