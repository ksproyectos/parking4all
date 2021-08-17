package com.ksproyectos.parking4all.Sync;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.ksproyectos.parking4all.DAO.SQL.AppDatabaseSingleton;
import com.ksproyectos.parking4all.DAO.SQL.Contracts.Parking4AllContract;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.DAO.SQL.Parking4AllDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parking4AllProvider extends ContentProvider {

    /*
     * Always return true, indicating that the
     * provider loaded correctly.
     */

    private static final int GET_All_MOVIMIENTOS = 1;

    private static final UriMatcher uriMatcher;
    static {
        // Add all our query types to our UriMatcher
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Parking4AllContract.CONTENT_AUTHORITY, Parking4AllContract.PATH_MOVIMIENTOS, GET_All_MOVIMIENTOS);
    }

    @Override
    public boolean onCreate() {
        return true;
    }
    /*
     * Return no type for MIME type
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
    /*
     * query() always returns no results
     *
     */
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        Cursor c;
        SimpleDateFormat sdf = new SimpleDateFormat(Const.DATE_FORMAT);
        Date fechaUltimaSincronizacion;
        Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase(getContext());

        try {
            fechaUltimaSincronizacion = sdf.parse(selectionArgs[0]);
            c = db.movimientosRoomDAO().getAllMovimientosEntradaSalidaByFechaDesdeHasta(fechaUltimaSincronizacion, new Date());

            return c;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }
    /*
     * insert() always returns null (no URI)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }
    /*
     * delete() always returns "no rows affected" (0)
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
    /*
     * update() always returns "no rows affected" (0)
     */
    public int update(
            Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {

        Parking4AllDatabase db = AppDatabaseSingleton.getInstance().getAppDatabase(getContext());


        Movimientos movimiento = new Movimientos();

        SimpleDateFormat df = new SimpleDateFormat(Const.DATE_FORMAT);

        movimiento.setIdMovimiento(values.getAsInteger(Parking4AllContract.Movimientos.COL_ID_MOVIMIENTO));
        movimiento.setIdMovimientoServidor(values.getAsString(Parking4AllContract.Movimientos.COL_ID_MOVIMIENTO_SERVIDOR));
        try {
            movimiento.setFechaEntrada(df.parse(values.getAsString(Parking4AllContract.Movimientos.COL_FECHA_ENTRADA)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String fechaSalidaString = values.getAsString(Parking4AllContract.Movimientos.COL_FECHA_SALIDA);

        if(null != fechaSalidaString){

            try {
                movimiento.setFechaSalida(df.parse(fechaSalidaString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        movimiento.setPlaca(values.getAsString(Parking4AllContract.Movimientos.COL_PLACA));
        movimiento.setTipoTarifa(values.getAsString(Parking4AllContract.Movimientos.COL_TIPO_TARIFA));
        movimiento.setIdUsuarioEntrada(values.getAsInteger(Parking4AllContract.Movimientos.COL_ID_USUARIO_ENTRADA));
        movimiento.setIdUsuarioSalida(values.getAsInteger(Parking4AllContract.Movimientos.COL_ID_USUARIO_SALIDA));
        movimiento.setValorTotal(values.getAsInteger(Parking4AllContract.Movimientos.COL_VALOR_TOTAL));

        db.movimientosRoomDAO().add(movimiento);

        return 1;

    }
}