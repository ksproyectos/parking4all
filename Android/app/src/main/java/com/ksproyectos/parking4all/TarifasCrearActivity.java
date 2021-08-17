package com.ksproyectos.parking4all;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.ksproyectos.parking4all.DAO.SQL.AppDatabaseSingleton;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Tarifas;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.MensualidadesBusiness;
import com.ksproyectos.parking4all.core.Business.TarifasBusiness;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

public class TarifasCrearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarifas_crear);

        Button buttonAgregarTarifa = findViewById(R.id.buttonAgregarTarifa);

        EditText editTextNombre = findViewById(R.id.editTextPlaca);

        RadioGroup radioGroupUnidadTiempo = findViewById(R.id.radioGroupUnidadTiempo);

        EditText editTextTiempo = findViewById(R.id.editTextTiempo);

        EditText editTextValor = findViewById(R.id.editTextValor);

        EditText editTextTipoTarifa = findViewById(R.id.editTextTipoTarifa);

        Context _this = this;

        TarifasBusiness tarifasBusiness = new TarifasBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()
        ));

        buttonAgregarTarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tarifas tarifa = new Tarifas();

                tarifa.setNombre(editTextNombre.getText().toString());

                switch (radioGroupUnidadTiempo.getCheckedRadioButtonId()){
                    case R.id.radioButtonDias:
                        tarifa.setDias(Integer.valueOf(editTextTiempo.getText().toString()));
                        break;
                    case R.id.radioButtonHoras:
                        tarifa.setHoras(Integer.valueOf(editTextTiempo.getText().toString()));
                        break;
                    case R.id.radioButtonMinutos:
                        tarifa.setMinutos(Integer.valueOf(editTextTiempo.getText().toString()));
                        break;
                }

                tarifa.setTarifa(Integer.valueOf(editTextValor.getText().toString()));

                tarifa.setTipoTarifa(editTextTipoTarifa.getText().toString());

                try {
                    tarifasBusiness.crearTarifa(tarifa);
                } catch (Parking4AllException e) {
                    e.printStackTrace();
                }

                ((TarifasCrearActivity) _this).finish();

            }
        });

    }

}
