package com.ksproyectos.parking4all;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Usuarios;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.CajaBusiness;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CajaActivity extends AppCompatActivity {

    TextView textViewTotal;
    TextView textViewFechaInicio;
    TextView textViewUsuario;
    Button buttonCerrarCaja;

    CajaBusiness cajaBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caja);

        textViewTotal = findViewById(R.id.textViewTotal);
        textViewFechaInicio = findViewById(R.id.textViewFechaInicio);
        textViewUsuario = findViewById(R.id.textViewUsuario);

        buttonCerrarCaja = findViewById(R.id.buttonCerrarCaja);

        cajaBusiness = new CajaBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()));

        buttonCerrarCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarCaja();
            }
        });

        mostrarResumenCajaActiva();
    }

    public void cerrarCaja(){
        Context  context = this;
        cajaBusiness.setOnCerrarCajaListener(new CajaBusiness.OnCerrarCajaListener() {
            @Override
            public void onCerrarCajaFailedPrint() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Atención");
                builder.setMessage("Ha fallado la impresion del ticket de cierre de caja. ¿Desea continuar con el cierre de caja?");

                builder.setPositiveButton("Si", (dialog, which) -> {
                    cajaBusiness.cerrarCaja(false);
                });
                builder.setNegativeButton("No", (dialog, which) -> {});

                builder.create();
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onCerrarCajaFailed(String errorMessage) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Atención");
                builder.setMessage("Ha fallado el cierre de caja. El mensaje de error del sistema es el siguiente: " + errorMessage);
                builder.setPositiveButton("Ok", (dialog, which) -> {});
                builder.create();
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onCerrarCajaSuccess() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Atención");
                builder.setMessage("Se ha realizado el cierre de caja correctamente.");
                builder.setPositiveButton("Ok", (dialog, which) -> {
                    finish();
                });
                builder.create();
                AlertDialog dialog = builder.create();
                dialog.show();

                mostrarResumenCajaActiva();
            }
        });

        cajaBusiness.cerrarCaja(true);
    }

    private void mostrarResumenCajaActiva(){
        Context  context = this;
        try {
            cajaBusiness.consultarCaja();
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Atención");
            builder.setMessage(e.getMessage());
            builder.setPositiveButton("Ok", (dialog, which) -> {});
            builder.create();
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        textViewTotal.setText(currencyFormat.format(cajaBusiness.getTotal()));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        textViewFechaInicio.setText(df.format(cajaBusiness.getFechaApertura()));
    }

}
