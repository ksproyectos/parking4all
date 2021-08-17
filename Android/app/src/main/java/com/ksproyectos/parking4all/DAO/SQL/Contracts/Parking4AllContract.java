package com.ksproyectos.parking4all.DAO.SQL.Contracts;

import android.net.Uri;

public class Parking4AllContract {

    // ContentProvider information
    public static final String CONTENT_AUTHORITY = "com.ksproyectos.parking4all.android.datasync.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIMIENTOS = "movimientos";
    public static final String PATH_CONFIGURACIONES = "configuraciones";

    public static abstract class Movimientos {
        public static final String NAME = "Movimientos";
        public static final String COL_ID_MOVIMIENTO = "IdMovimiento";
        public static final String COL_ID_MOVIMIENTO_SERVIDOR = "IdMovimientoServidor";

        public static final String COL_PLACA = "Placa";
        public static final String COL_FECHA_ENTRADA = "FechaEntrada";
        public static final String COL_FECHA_SALIDA = "FechaSalida";
        public static final String COL_FINALIZADO = "Finalizado";
        public static final String COL_TIPO_TARIFA= "TipoTarifa";
        public static final String COL_VALOR_TOTAL = "ValorTotal";
        public static final String COL_ID_USUARIO_ENTRADA = "IdUsuarioEntrada";
        public static final String COL_ID_USUARIO_SALIDA = "IdUsuarioSalida";


        // ContentProvider information for articles
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIMIENTOS).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_MOVIMIENTOS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_MOVIMIENTOS;
    }

    public static abstract class Configuraciones {

        // ContentProvider information for articles
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONFIGURACIONES).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_CONFIGURACIONES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CONFIGURACIONES;
    }

}
