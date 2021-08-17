package com.ksproyectos.parking4all.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Debug;
import android.os.RemoteException;
import android.system.Os;

import com.ksproyectos.parking4all.DAO.SQL.Contracts.Parking4AllContract;
import com.squareup.okhttp.internal.Platform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.client.ApiException;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.MovimientosDTO;
import io.swagger.client.model.PullSyncResponse;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver contentResolver;

    private String mToken;
    private AccountManager mAccountManager;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        contentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        contentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {

        DefaultApi api = new DefaultApi();
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();

        String fechaUltimaSincronizacion = "";

        try {
            DateFormat df = new SimpleDateFormat(Const.DATE_FORMAT);

            PullSyncResponse pullResponse =  api.movimientosGet("1");

            fechaUltimaSincronizacion = pullResponse.getFechaUltimaSincronizacion();

            List<MovimientosDTO> movimientosDTOList = pullResponse.getItems();

            for (MovimientosDTO movimientosDTO : movimientosDTOList){

                batch.add(ContentProviderOperation.newUpdate(Parking4AllContract.Movimientos.CONTENT_URI)

                        .withValue(Parking4AllContract.Movimientos.COL_ID_MOVIMIENTO, movimientosDTO.getIdMovimientoTerminal())
                        .withValue(Parking4AllContract.Movimientos.COL_ID_MOVIMIENTO_SERVIDOR, movimientosDTO.getIdMovimientoServidor())
                        .withValue(Parking4AllContract.Movimientos.COL_FECHA_ENTRADA, movimientosDTO.getFechaEntrada())
                        .withValue(Parking4AllContract.Movimientos.COL_FECHA_SALIDA, movimientosDTO.getFechaSalida())
                        .withValue(Parking4AllContract.Movimientos.COL_PLACA, movimientosDTO.getPlaca())
                        .withValue(Parking4AllContract.Movimientos.COL_TIPO_TARIFA, movimientosDTO.getCategoria())
                        .withValue(Parking4AllContract.Movimientos.COL_VALOR_TOTAL, movimientosDTO.getValorTotal())
                        .withValue(Parking4AllContract.Movimientos.COL_ID_USUARIO_ENTRADA, movimientosDTO.getUsuarioEntrada())
                        .withValue(Parking4AllContract.Movimientos.COL_ID_USUARIO_SALIDA, movimientosDTO.getUsuarioSalida())

                        .build());

            }

            try {
                provider.applyBatch(batch);
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } catch (ApiException e) {
            e.printStackTrace();
        }

        Cursor c;

        try {
            String[] args = {fechaUltimaSincronizacion};
            c = provider.query(Parking4AllContract.Movimientos.CONTENT_URI, null, null, args, null);

            if(null != c && c.getCount() > 0) {

                SimpleDateFormat df = new SimpleDateFormat(Const.DATE_FORMAT);

                c.moveToFirst();

                do{
                    MovimientosDTO movimientosDTO = new MovimientosDTO();

                    movimientosDTO.setIdMovimientoTerminal(c.getString(c.getColumnIndex(Parking4AllContract.Movimientos.COL_ID_MOVIMIENTO)));
                    movimientosDTO.setFechaEntrada(df.format(new Date(c.getLong(c.getColumnIndex(Parking4AllContract.Movimientos.COL_FECHA_ENTRADA)))));

                    Long fechaSalidaLong = c.getLong(c.getColumnIndex(Parking4AllContract.Movimientos.COL_FECHA_SALIDA));

                    if(null != fechaSalidaLong && 0L != fechaSalidaLong){
                        movimientosDTO.setFechaSalida(df.format(new Date(fechaSalidaLong)));
                    }

                    movimientosDTO.setPlaca(c.getString(c.getColumnIndex(Parking4AllContract.Movimientos.COL_PLACA)));
                    movimientosDTO.setCategoria(c.getString(c.getColumnIndex(Parking4AllContract.Movimientos.COL_TIPO_TARIFA)));
                    movimientosDTO.setValorTotal(String.valueOf(c.getInt(c.getColumnIndex(Parking4AllContract.Movimientos.COL_VALOR_TOTAL))));
                    movimientosDTO.setUsuarioEntrada(c.getString(c.getColumnIndex(Parking4AllContract.Movimientos.COL_ID_USUARIO_ENTRADA)));
                    movimientosDTO.setUsuarioSalida(c.getString(c.getColumnIndex(Parking4AllContract.Movimientos.COL_ID_USUARIO_SALIDA)));

                    movimientosDTO.setIdTerminal("1");

                    try {
                        api.movimientosPost(movimientosDTO);
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }

                }while(c.moveToNext());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }




    }
}