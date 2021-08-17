package com.ksproyectos.parking4all;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Mensualidades;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.MensualidadesBusiness;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import java.util.Date;
import java.util.List;

public class MensualidadesCrearActivity extends AppCompatActivity {

    private MensualidadesBusiness mensualidadesBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensualidades_crear);
        setTitle("Crear mensualidades");

        mensualidadesBusiness = new MensualidadesBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()
        ));

        Button buttonRegistrarMensualidad = findViewById(R.id.buttonRegistrarMensualidad);

        EditText editTextPlaca = findViewById(R.id.editTextPlaca);
        EditText editTextNombres = findViewById(R.id.editTextNombres);
        EditText editTextNumeroDocumento = findViewById(R.id.editTextNumeroDocumento);
        EditText editTextTelefono = findViewById(R.id.editTextTelefono);

        editTextPlaca.setFilters(new InputFilter[] {new InputFilter.AllCaps()});


        Context _this = this;

        buttonRegistrarMensualidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mensualidades mensualidad = new Mensualidades();

                mensualidad.setPlaca(editTextPlaca.getText().toString());
                mensualidad.setNombres(editTextNombres.getText().toString());
                mensualidad.setNumeroDocumento(editTextNumeroDocumento.getText().toString());
                mensualidad.setTelefono(editTextTelefono.getText().toString());

                List<String> options
                        = mensualidadesBusiness.getListaTarifasDisponibles();

                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(_this);
                builder.setTitle("Tarifa");


                CharSequence[] cs = options.toArray(new CharSequence[options.size()]);

                builder.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String tipoTarifa = "";

                        tipoTarifa = options.get(which);

                        try {
                            mensualidadesBusiness.crearMensualidad(
                                    mensualidad.getNombres()
                                    , mensualidad.getNumeroDocumento()
                                    , mensualidad.getPlaca()
                                    , mensualidad.getTelefono()
                                    , tipoTarifa);

                            mensualidadesBusiness.imprimirTicketMensualidad();

                            ((MensualidadesCrearActivity) _this).finish();

                        } catch (Parking4AllException e) {
                            e.printStackTrace();

                            AlertDialog.Builder errorAlert = new AlertDialog.Builder(_this);
                            errorAlert.setTitle("Atención");
                            errorAlert.setMessage("No se pudo crear la mensualidad. El mensaje de error del sistema es: "+ e.getMessage());
                            errorAlert.show();
                        }

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

}
