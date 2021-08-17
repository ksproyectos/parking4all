package com.ksproyectos.parking4all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Mensualidades;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.MensualidadesBusiness;
import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import java.util.ArrayList;
import java.util.List;

public class MensualidadesListActivity extends AppCompatActivity {


    ListView listView;

    MensualidadesListCustomAdapter listCustomAdapter = new MensualidadesListCustomAdapter(this);

    MensualidadesBusiness mensualidadesBusiness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);
        setTitle("Mensualidades");

        mensualidadesBusiness = new MensualidadesBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()
        ));

        FloatingActionButton fabCrear = findViewById(R.id.fabCrear);

        Context _this = this;

        fabCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_this, MensualidadesCrearActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.listView);

        listCustomAdapter.setButtonEliminarListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mensualidades mensualidad = new Mensualidades();

                mensualidad.setIdMensualidad((Integer) v.getTag());

                try {
                    mensualidadesBusiness.eliminarMensualidad(mensualidad);
                } catch (Parking4AllException e) {
                    e.printStackTrace();
                }

                refreshListViewDataSet();
            }
        });

        listView.setAdapter(listCustomAdapter);

    }

    @Override
    protected void onStart(){
        super.onStart();

        refreshListViewDataSet();

    }



    private void refreshListViewDataSet(){
        List<IMensualidades> list = mensualidadesBusiness.obtenerMensualidadesActivas();
        if(list == null){
            list = new ArrayList<>();
        }
        listCustomAdapter.getList().clear();
        listCustomAdapter.getList().addAll(list);

        listCustomAdapter.notifyDataSetChanged();
    }


}
