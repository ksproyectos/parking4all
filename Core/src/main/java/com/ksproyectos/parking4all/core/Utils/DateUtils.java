/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.core.Utils;

import java.util.Date;

/**
 *
 * @author kevin
 */
public class DateUtils {
    
     public static Long getDateDiff(Date fechaInicial, Date fechaFinal,
            String type) {

        Long diff = fechaFinal.getTime() - fechaInicial.getTime();

        Long dias = diff / (1000 * 60 * 60 * 24);

        Long horas = (diff / (1000 * 60 * 60)) - (dias * 24);

        Long minutos = (diff / (1000 * 60)) - (dias * 24 * 60) - (horas * 60);

        switch (type) {
            case "Days":
                return dias;
            case "Hours":
                return horas;
            case "Minutes":
                return minutos;
        }
        return null;
    }
    
    public static Long getDateDiffDays(Date fechaInicio, Date fechaFin){
        return getDateDiff(fechaInicio, fechaFin, "Days");
    }
    
    public static Long getDateDiffHours(Date fechaInicio, Date fechaFin){
        return getDateDiff(fechaInicio, fechaFin, "Hours");
    }
    
    public static Long getDateDiffMinutes(Date fechaInicio, Date fechaFin){
        return getDateDiff(fechaInicio, fechaFin, "Minutes");
    }
    
}
