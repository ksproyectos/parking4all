package com.ksproyectos.parking4all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.ksproyectos.parking4all.DAO.SQL.AppDatabaseSingleton;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Tarifas;

import java.util.ArrayList;
import java.util.List;

public class TarifasListActivity extends AppCompatActivity {


    ListView listView;

    TarifasListCustomAdapter tarifasListCustomAdapter = new TarifasListCustomAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);
        setTitle("Tarifas");

        FloatingActionButton fabCrearTarifa = findViewById(R.id.fabCrear);

        Context _this = this;

        fabCrearTarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_this, TarifasCrearActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.listView);

        tarifasListCustomAdapter.setButtonEliminarListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tarifas tarifa = new Tarifas();
                tarifa.setIdTarifa((Integer) v.getTag());

                AppDatabaseSingleton.getInstance().getAppDatabase().tarifasRoomDAO().delete(tarifa);

                refreshListViewDataSet();
            }
        });


        listView.setAdapter(tarifasListCustomAdapter);

    }

    @Override
    protected void onStart(){
        super.onStart();

        refreshListViewDataSet();

    }



    private void refreshListViewDataSet(){
        List<Tarifas> tarifasList = AppDatabaseSingleton.getInstance()
                .getAppDatabase()
                .tarifasRoomDAO()
                .getAllTarifas();

        if(tarifasList == null){
            tarifasList = new ArrayList<>();
        }
        tarifasListCustomAdapter.getTarifasList().clear();
        tarifasListCustomAdapter.getTarifasList().addAll(tarifasList);

        tarifasListCustomAdapter.notifyDataSetChanged();
    }


}
